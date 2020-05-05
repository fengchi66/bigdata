package demo.window

import java.util
import java.util.Collections

import demo.bean.SensorReading
import demo.utils.SensorSource
import org.apache.flink.api.common.ExecutionConfig
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeutils.TypeSerializer
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.{TimeCharacteristic, environment}
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{EventTimeTrigger, Trigger}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-04-04 1:21 下午
 *        自定义窗口分配器、触发器、剔除器
 */
object CustomWindows {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getCheckpointConfig.setCheckpointInterval(10 * 1000) //checkpoint周期
    env.getConfig.setAutoWatermarkInterval(1000) // watermark生成周期

    val sensorData = env.addSource(new SensorSource)
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(5)) {
        override def extractTimestamp(element: SensorReading): Long = element.timeStamp
      })

    sensorData
      .keyBy(_.id)
      .window(new ThirtySecondsWindows) // 将事件按照每30秒滚动窗口的进行分组的窗口分配器
      .allowedLateness(Time.seconds(10))  //定义延迟容忍度为50S的窗口算子
//      .process(new UpdateWindowCountFunction)
  }

}

class ThirtySecondsWindows extends WindowAssigner[Object,TimeWindow]{

  val windowSize :Long = 30 * 1000L

  override def assignWindows(element: Object,
                             timestamp: Long,
                             context: WindowAssigner.WindowAssignerContext): util.Collection[TimeWindow] = {
//    30S取余
    val startTime = timestamp - (timestamp % windowSize)
    val endTime = startTime + windowSize
//    发出相应的时间窗口
    Collections.singletonList(new TimeWindow(startTime,endTime))
  }

//  返回WindowAssigner的默认触发器
  override def getDefaultTrigger(env: environment.StreamExecutionEnvironment): Trigger[Object, TimeWindow] = {
    EventTimeTrigger.create()
  }

  override def getWindowSerializer(executionConfig: ExecutionConfig): TypeSerializer[TimeWindow] = {
    new TimeWindow.Serializer
  }

//  是否基于eventTime
  override def isEventTime: Boolean = true
}

class UpdateWindowCountFunction extends ProcessWindowFunction[SensorSource,(String,Int,Long,String),String,TimeWindow]{
//  定义状态用于标识是否是第一次对窗口进行计算
  private var updateState: ValueState[Boolean] = _

//  override def open(parameters: Configuration): Unit = {
//    val uodateStateDec = new ValueStateDescriptor[Boolean]("update-state", classOf[Boolean])
//    updateState = getRuntimeContext.getState(uodateStateDec)
//  }

  override def process(key: String,
                       context: Context,
                       elements: Iterable[SensorSource],
                       out: Collector[(String, Int, Long, String)]): Unit = {
    val count = elements.count(_ => true)

    val uodateStateDec = new ValueStateDescriptor[Boolean]("update-state", classOf[Boolean])
    updateState = context.windowState.getState(uodateStateDec)

    if (!updateState.value()){
//      第一次计算
      out.collect((key,count,context.window.getEnd,"first"))
    }else{
//      并非首次计算，发出更新
      out.collect((key,count,context.window.getEnd,"update"))
    }

  }
}
