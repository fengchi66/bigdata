package demo.window

import java.text.SimpleDateFormat

import demo.bean.OrderLogBean
import demo.utils.DateUtils
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{ContinuousEventTimeTrigger, CountTrigger, EventTimeTrigger, Trigger, TriggerResult}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-04-19 11:19 上午
 */
object TriggerAndEvictorDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    env.fromCollection(List(
      OrderLogBean(1L,"pen",1,DateUtils.getTime("2020-04-19 11:00")),
      OrderLogBean(1L,"pen",2,DateUtils.getTime("2020-04-19 11:01")),
      OrderLogBean(1L,"pen",3,DateUtils.getTime("2020-04-19 11:02")),
      OrderLogBean(1L,"pen",4,DateUtils.getTime("2020-04-19 11:03")),
      OrderLogBean(1L,"pen",5,DateUtils.getTime("2020-04-19 11:04")),
      OrderLogBean(1L,"pen",1,DateUtils.getTime("2020-04-19 11:05")),
      OrderLogBean(1L,"pen",2,DateUtils.getTime("2020-04-19 11:06")),
      OrderLogBean(1L,"pen",3,DateUtils.getTime("2020-04-19 11:07")),
      OrderLogBean(1L,"pen",4,DateUtils.getTime("2020-04-19 11:08")),
      OrderLogBean(1L,"pen",5,DateUtils.getTime("2020-04-19 11:09"))
    ))
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[OrderLogBean](Time.seconds(5)) {
        override def extractTimestamp(element: OrderLogBean): Long = element.timeStamp
      })
      .keyBy(_.userId)
//      .window(TumblingEventTimeWindows.of(Time.minutes(5)))
      .timeWindow(Time.minutes(5))
//      .trigger(EventTimeTrigger.create())
//        .trigger(CountTrigger.of(2))
//      .trigger(new MyCountTrigger(2L))
      .trigger(ContinuousEventTimeTrigger.of(Time.minutes(2)))

        .aggregate(new AggWindowFunction,new WindowResultCount)
        .print()


    env.execute("Test")

  }

}

class AggWindowFunction extends AggregateFunction[OrderLogBean,Int,Int]{
  override def createAccumulator(): Int = 0

  override def add(value: OrderLogBean, accumulator: Int): Int = accumulator + value.amount

  override def getResult(accumulator: Int): Int = accumulator

  override def merge(a: Int, b: Int): Int = a + b
}

class WindowResultCount extends ProcessWindowFunction[Int,(Long,Int,String),Long,TimeWindow]{
  override def process(key: Long,
                       context: Context,
                       elements: Iterable[Int],
                       out: Collector[(Long, Int, String)]): Unit = {
    out.collect((key,elements.iterator.next(),DateUtils.getDate(context.window.getEnd)))
  }
}

class MyCountTrigger(maxCount: Long) extends Trigger[Object,TimeWindow]{

//  注册状态，存数据到达的次数
  private var countState: ValueState[Long] = _

  override def onElement(element: Object,
                         timestamp: Long,
                         window: TimeWindow,
                         ctx: Trigger.TriggerContext): TriggerResult = {
    val countStateDec = new ValueStateDescriptor[Long]("count-state", classOf[Long],0L)
    countState = ctx.getPartitionedState(countStateDec)

    val count = countState.value() + 1L

    countState.update(count)

    if (count >= maxCount){
      countState.clear()
      TriggerResult.FIRE_AND_PURGE
    }else{
      TriggerResult.CONTINUE
    }

  }

  override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
    TriggerResult.CONTINUE
  }

  override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
    TriggerResult.CONTINUE
  }

  override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = {
    countState.clear()
  }
}






