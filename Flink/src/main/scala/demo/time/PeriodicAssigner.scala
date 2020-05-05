package demo.time

import demo.bean.SensorReading
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

/**
 * @author wufc
 * @create 2020-04-03 3:43 下午
 *        周期性水位线分配器  Flink源码中BoundedOutOfOrdernessGenerator也是这种方式实现的
 */
class PeriodicAssigner extends AssignerWithPeriodicWatermarks[SensorReading]{
  val bound: Long = 2000 //延迟时长为2秒
  var maxTs: Long = Long.MaxValue //观察到的最大时间戳

//  watermark生成器，周期性执行
  override def getCurrentWatermark: Watermark = {
    new Watermark(maxTs - bound)
  }

//  当前函数返回事件的时间戳
  override def extractTimestamp(element: SensorReading, previousElementTimestamp: Long): Long = {
//    更新最大时间戳
    maxTs = maxTs.max(element.timeStamp)
    element.timeStamp
  }
}
