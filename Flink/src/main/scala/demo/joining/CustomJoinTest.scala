package demo.joining

import demo.bean.{OrderLogEvent1, OrderLogEvent2}
import demo.utils.DateUtils
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-05-05 5:18 下午
 *         自定义双流Join：如果只需要关心最新的数据，而社区的join，重复的key会产生重复数据，
 *         所以滴滴内部内置了自定义的双流join实现，将两条流数据存储到带ttl的state里，假如存在相同的key有多条数据，
 *         比如订单重复数据，新的订单数据覆盖掉相同key的老订单数据即可。
 */
object CustomJoinTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val leftOrderStream = env.fromCollection(List(
      OrderLogEvent1(1L, 22.1, DateUtils.getTime("2020-04-29 13:01")),
      OrderLogEvent1(2L, 22.2, DateUtils.getTime("2020-04-29 13:03")),
      OrderLogEvent1(4L, 22.3, DateUtils.getTime("2020-04-29 13:04")),
      OrderLogEvent1(4L, 22.4, DateUtils.getTime("2020-04-29 13:05")),
      OrderLogEvent1(5L, 22.5, DateUtils.getTime("2020-04-29 13:07")),
      OrderLogEvent1(6L, 22.6, DateUtils.getTime("2020-04-29 13:09"))
    ))
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[OrderLogEvent1](Time.seconds(5)) {
        override def extractTimestamp(element: OrderLogEvent1): Long = element.timeStamp
      })
      .keyBy(_.orderId)
      .process(new DistinctOrder1Function)

    val rightOrderStream = env.fromCollection(List(
      OrderLogEvent2(1L, 121, DateUtils.getTime("2020-04-29 13:01")),
      OrderLogEvent2(2L, 122, DateUtils.getTime("2020-04-29 13:03")),
      OrderLogEvent2(3L, 123, DateUtils.getTime("2020-04-29 13:04")),
      OrderLogEvent2(4L, 124, DateUtils.getTime("2020-04-29 13:05")),
      OrderLogEvent2(5L, 125, DateUtils.getTime("2020-04-29 13:07")),
      OrderLogEvent2(7L, 126, DateUtils.getTime("2020-04-29 13:09"))
    ))
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[OrderLogEvent2](Time.seconds(5)) {
        override def extractTimestamp(element: OrderLogEvent2): Long = element.timeStamp
      })
      .keyBy(_.orderId)

  }

}

class DistinctOrder1Function extends KeyedProcessFunction[Long, OrderLogEvent1, OrderLogEvent1] {

  //  定义状态，存储最新的订单数据
  private var orderState: ValueState[OrderLogEvent1] = _


  override def open(parameters: Configuration): Unit = {
    //    状态描述器
    val orderStateDec = new ValueStateDescriptor[OrderLogEvent1]("order-state", classOf[OrderLogEvent1])
    orderState = getRuntimeContext.getState(orderStateDec)
  }

  override def processElement(value: OrderLogEvent1,
                              ctx: KeyedProcessFunction[Long, OrderLogEvent1, OrderLogEvent1]#Context,
                              out: Collector[OrderLogEvent1]): Unit = {
//    跟新状态
    orderState.update(value)

  }
}
