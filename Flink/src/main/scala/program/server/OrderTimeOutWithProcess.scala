package program.server

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import program.bean.{OrderEvent, OrderResult}

/**
 * @author wufc
 * @create 2020-04-02 3:21 下午
 */
object OrderTimeOutWithProcess {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(5000)  //生成watermark的周期
    env.setParallelism(1)
    //元素在网络上传输时会进行缓冲，为平衡吞吐量与延迟可以设置缓冲区填充的最大等待时间。
    // 在此时间之后，即使缓冲区未满，也会自动发送缓冲区。此超时的默认值为100毫秒。为了尽可能减小延迟，可设置为与0接近的值。
    env.setBufferTimeout(-1)

    env.readTextFile("Flink/Data/OrderLog.csv")
      .map(line => {
        val arr = line.split(",", -1)
        OrderEvent(arr(0).trim.toLong, arr(1).trim, arr(3).trim.toLong * 1000)
      })
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[OrderEvent](Time.seconds(2)) {
        override def extractTimestamp(t: OrderEvent): Long = t.eventTime
      })
      .keyBy(_.orderId)
      .process(new OrderTimeOutProcessFunction)
      .print()

    env.execute("OrderTimeOutWithProcess")
  }

}

class OrderTimeOutProcessFunction extends KeyedProcessFunction[Long, OrderEvent, OrderResult] {
  //  定义状态，表示订单是否被支付,订单支付了变为true
  private var payedState: ValueState[Boolean] = _

  override def open(parameters: Configuration): Unit = {
    //    状态描述器
    val payedStateDec = new ValueStateDescriptor[Boolean]("payed-state", classOf[Boolean])
    payedState = getRuntimeContext.getState(payedStateDec)
  }

  //  对每一条来的数据
  override def processElement(i: OrderEvent,
                              context: KeyedProcessFunction[Long, OrderEvent, OrderResult]#Context,
                              collector: Collector[OrderResult]): Unit = {
    //    取出状态
    val bool = payedState.value()
    //    当前事件为创建 且状态为false，表示当前订单还没有支付，则注册15分钟后的定时器
    if (i.eventType == "create" && !bool) {
      context.timerService().registerEventTimeTimer(i.eventTime + 15 * 60 * 1000L)
    } else if (i.eventType == "pay") { //当前日志为支付,则状态更新为true
      payedState.update(true)
    }
  }

  //  定时器触发
  override def onTimer(timestamp: Long,
                       ctx: KeyedProcessFunction[Long, OrderEvent, OrderResult]#OnTimerContext,
                       out: Collector[OrderResult]): Unit = {
    //    定时器触发时，取出状态，判断该订单是否已支付
    val bool = payedState.value()
    if (!bool) { //状态为false，表明该订单15分钟后还未支付
      out.collect(OrderResult(ctx.getCurrentKey, "timeout"))
    }
    payedState.clear()
  }
}
