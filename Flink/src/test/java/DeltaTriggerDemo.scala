//import demo.bean.SensorReading
//import demo.utils.SensorSource
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.windowing.triggers.{ContinuousEventTimeTrigger, DeltaTrigger, PurgingTrigger, Trigger, TriggerResult}
//import org.apache.flink.streaming.api.windowing.windows.TimeWindow
//
///**
// * @author wufc
// * @create 2020-04-18 6:48 下午
// */
//object DeltaTriggerDemo {
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//
//    env.addSource(new SensorSource)
//      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(2)) {
//        override def extractTimestamp(element: SensorReading): Long = element.timeStamp
//      })
//      .keyBy(_.id)
//      .timeWindow(Time.minutes(5))
//      .trigger(PurgingTrigger.of(ContinuousEventTimeTrigger.of(Time.minutes(2))))
//
//
//
//  }
//
//}
//
//class aaa extends Trigger[SensorReading,TimeWindow]{
//
//
//  override def canMerge: Boolean = super.canMerge
//
//  override def onMerge(window: TimeWindow, ctx: Trigger.OnMergeContext): Unit = super.onMerge(window, ctx)
//
//  override def onElement(element: SensorReading, timestamp: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = ???
//
//  override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = ???
//
//  override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = ???
//
//  override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = ???
//}
