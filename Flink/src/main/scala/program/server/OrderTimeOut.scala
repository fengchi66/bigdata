package program.server

import java.util

import org.apache.flink.cep.{PatternSelectFunction, PatternTimeoutFunction}
import org.apache.flink.cep.scala.{CEP, PatternStream}
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import program.bean.{OrderEvent, OrderResult}

/**
 * @author wufc
 * @create 2020-04-02 1:02 下午
 *         订单超过15分钟未支付则取消订单
 */
object OrderTimeOut {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(5000)
    env.setParallelism(1)

    val dataSourceStream: DataStream[OrderEvent] = env.readTextFile("Flink/Data/OrderLog.csv")
      .map(line => {
        val arr = line.split(",", -1)
        OrderEvent(arr(0).trim.toLong, arr(1).trim, arr(3).trim.toLong * 1000)
      })
      .assignAscendingTimestamps(_.eventTime)
      .keyBy(_.orderId)

    //    定义一个匹配模式
    val orderPayPattern: Pattern[OrderEvent, OrderEvent] = Pattern.begin[OrderEvent]("begin").where(_.eventType == "create")
      .followedBy("follow").where(_.orderId == "pay")
      .within(Time.minutes(15))

    //    把模式应用到Stream上，得到一个Pattern Stream
    val orderPayPatternStream: PatternStream[OrderEvent] = CEP.pattern(dataSourceStream, orderPayPattern)

    //    定义一个侧输出标签，超时事件流侧输出
    val orderTimeOutOutPutTag = new OutputTag[OrderResult]("OrderTimeOut")

//    调用select方法，提取事件序列,超时的事件要做报警提示
    /**
     * def select[L: TypeInformation, R: TypeInformation](
     * outputTag: OutputTag[L],
     * patternTimeoutFunction: PatternTimeoutFunction[T, L],
     * patternSelectFunction: PatternSelectFunction[T, R])
     * : DataStream[R] = {
     */
    val resultStream: DataStream[OrderResult] = orderPayPatternStream.select(
      orderTimeOutOutPutTag,
      new OrderTimeOutSelectFunction,
      new OrderPaySelectFunction
    )

    resultStream.print("payed")
    resultStream.getSideOutput(orderTimeOutOutPutTag).print("timeout")

    env.execute("OrderTimeOut")

  }

}

// 自定义超时事件序列处理函数
class OrderTimeOutSelectFunction extends PatternTimeoutFunction[OrderEvent,OrderResult]{
  override def timeout(map: util.Map[String, util.List[OrderEvent]], l: Long): OrderResult = {
    val orderId =map.get("begin").iterator().next().orderId
    OrderResult(orderId,"timeout")
  }
}

// 自定义正常支付事件序列处理函数
class OrderPaySelectFunction extends PatternSelectFunction[OrderEvent,OrderResult]{
  override def select(map: util.Map[String, util.List[OrderEvent]]): OrderResult = {
    val orderId = map.get("follow").iterator().next().orderId
    OrderResult(orderId,"payed success")
  }
}
