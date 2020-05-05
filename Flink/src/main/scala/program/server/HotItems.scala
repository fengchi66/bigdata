package program.server

import com.sun.jmx.snmp.Timestamp
import org.apache.flink.streaming.api.scala._
import demo.utils.FlinkUtils
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.{KeyedStateStore, ListState, ListStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import program.bean.{ItemViewCount, UserBehavior}

import scala.collection.mutable.ListBuffer

/**
 * @author wufc
 * @create 2020-04-01 3:01 下午
 *         实时热门商品统计：每隔5分钟输出最近一小时内点击量最多的前N个商品
 */
object HotItems {
  def main(args: Array[String]): Unit = {
    val env = FlinkUtils.getStreamEnv()
    //    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime) //使用事件时间
    env.getConfig.setAutoWatermarkInterval(5000) //每5秒生成一次watermark


    val dataSourceStream: DataStream[UserBehavior] = env.addSource(FlinkUtils.getFlinkKafkaConsumer())
      .map(line => {
        val arr = line.split(",", -1)
        UserBehavior(arr(0).trim.toLong, arr(1).trim.toLong, arr(2).trim.toInt, arr(3), arr(4).toLong)
      }).filter(_.behavior == "pv")
      // 分配eventTime与设置waterMark
      .assignTimestampsAndWatermarks(
        new BoundedOutOfOrdernessTimestampExtractor[UserBehavior](Time.seconds(5)) { //设置延时时长
          override def extractTimestamp(t: UserBehavior): Long = t.timestamp
        })

    dataSourceStream
      .keyBy(_.itemId)
      .timeWindow(Time.hours(1), Time.minutes(5))
      //      .allowedLateness(Time.minutes(1))
      .aggregate(new ItemCountAgg, new WindowResultFunction) //计算出（itemId,wnidowEnd,count）
      .keyBy(_.windowEnd)
      .process(new TopNHotItems(3))
      .print()

    env.execute("HotItems")

  }

}

class ItemCountAgg extends AggregateFunction[UserBehavior, Int, Int] {
  override def createAccumulator(): Int = 0

  override def add(in: UserBehavior, acc: Int): Int = acc + 1

  override def getResult(acc: Int): Int = acc

  override def merge(acc: Int, acc1: Int): Int = acc + acc1
}

class WindowResultFunction extends ProcessWindowFunction[Int, ItemViewCount, Long, TimeWindow] {
  override def process(key: Long,
                       context: Context,
                       elements: Iterable[Int],
                       out: Collector[ItemViewCount]): Unit = {
    out.collect(ItemViewCount(key, context.window.getEnd, elements.iterator.next()))
  }
}

class TopNHotItems(topSize: Int) extends KeyedProcessFunction[Long, ItemViewCount, String] {
  //  定义一个状态，用来存放当前窗口里的每一条数据
  private var itemState: ListState[ItemViewCount] = _

  //  open函数中创建状态对象
  override def open(parameters: Configuration): Unit = {
    //    状态描述器
    val itemStateDes = new ListStateDescriptor[ItemViewCount]("item-state", classOf[ItemViewCount])

    itemState = getRuntimeContext.getListState(itemStateDes)
  }

  //  每条数据将执行的函数
  override def processElement(i: ItemViewCount,
                              context: KeyedProcessFunction[Long, ItemViewCount, String]#Context,
                              collector: Collector[String]): Unit = {
    //    将每一条数据都放入到状态中
    itemState.add(i)

    //    注册定时器，保证定时器响的时候，状态中已收集到该窗口的所有数据
    //    当watermark>=windowEnd时窗口触发计算。所以windowEnd + 1，说明此时，window一定触发计算，状态中收集到了所有元素
    context.timerService().registerEventTimeTimer(i.windowEnd + 1)

  }

  //  回调函数
  override def onTimer(timestamp: Long,
                       ctx: KeyedProcessFunction[Long, ItemViewCount, String]#OnTimerContext,
                       out: Collector[String]): Unit = {
    val allItems = new ListBuffer[ItemViewCount]
    import scala.collection.JavaConversions._
    for (item <- itemState.get()) {
      allItems += item
    }

    //    清除状态中的数据，释放空间
    itemState.clear()

    // 按照点击量从大到小排序
    val sortedItems = allItems.sortBy(_.count)(Ordering.Long.reverse).take(topSize)
    // 将排名信息格式化成 String, 便于打印
    val result: StringBuilder = new StringBuilder
    result.append("====================================\n")
    result.append("时间: ").append(new Timestamp(timestamp - 1)).append("\n")

    for (i <- sortedItems.indices) {
      val currentItem: ItemViewCount = sortedItems(i)
      // e.g.  No1：  商品ID=12224  浏览量=2413
      result.append("No").append(i + 1).append(":")
        .append("  商品ID=").append(currentItem.itemId)
        .append("  浏览量=").append(currentItem.count).append("\n")
    }
    result.append("====================================\n\n")
    // 控制输出频率，模拟实时滚动结果
    Thread.sleep(1000)
    out.collect(result.toString)
  }
}
