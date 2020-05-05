package process

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-04-04 8:16 下午
 *         每隔一分钟发出一次key的计数
 */
case class CountWithTimestamp(key: String, count: Long, lastModified: Long)

object SessionCountKeyedProcessFunction {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    env.socketTextStream("localhost", 9999)
      .map(line => {
        val arr = line.split(",", -1)
        (arr(0), arr(1)) // (id,name)
      })
      .keyBy(_._1) // 以id keyBy
      .process(new CountWithTimeoutFunction)

  }

}

class CountWithTimeoutFunction extends KeyedProcessFunction[String, (String, String), (String, Long)] {
  //  定义状态，保存当前key的积累次数及最后更新时间
  private lazy val state: ValueState[CountWithTimestamp] = getRuntimeContext.getState[CountWithTimestamp](
    new ValueStateDescriptor[CountWithTimestamp]("myState", classOf[CountWithTimestamp])
  )

  override def processElement(value: (String, String),
                              ctx: KeyedProcessFunction[String, (String, String), (String, Long)]#Context,
                              out: Collector[(String, Long)]): Unit = {
    //    从状态中取出数据并更新结果
    val current: CountWithTimestamp = state.value() match {
      //        状态为空，表示当前key是第一次计算
      case null => CountWithTimestamp(value._1, 1, ctx.timestamp())
      //        当前状态有值，则更新
      case CountWithTimestamp(id, count, timeStamp) => CountWithTimestamp(id, count + 1, ctx.timestamp())
    }

    //    更新状态
    state.update(current)

    //    注册一个定时器，一分钟之后响的时候发出结果
    ctx.timerService().registerEventTimeTimer(current.lastModified + 60 * 1000)

    //    由于Flink每个键和时间戳仅维护一个计时器，因此可以通过降低计时器分辨率以合并它们来减少计时器的数量。
    //对于1秒（事件或处理时间）的计时器分辨率，可以将目标时间舍入为整秒。计时器最多可提前1秒触发，但不晚于毫秒精度。结果，每个按键和秒最多有一个计时器。

    //    val coalescedTime = ((ctx.timestamp + timeout) / 1000) * 1000
    //    ctx.timerService.registerProcessingTimeTimer(coalescedTime)

  }

  //  定时器触发
  override def onTimer(timestamp: Long,
                       ctx: KeyedProcessFunction[String, (String, String), (String, Long)]#OnTimerContext,
                       out: Collector[(String, Long)]): Unit = {
    //    输出
    state.value match {
      case CountWithTimestamp(key, count, lastModified) if (timestamp == lastModified + 60000) =>
        out.collect((key, count))
      case _ =>
    }

    //    删除计时器
    ctx.timerService().deleteEventTimeTimer(timestamp)

    //    清理状态
    state.clear()
  }
}
