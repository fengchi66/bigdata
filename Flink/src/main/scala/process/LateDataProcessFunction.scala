package process

import demo.bean.SensorReading
import demo.utils.SensorSource
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-04-04 10:48 上午
 *         用于功率出迟到数据将其重定向到侧输出的processFunction
 */
object LateDataProcessFunction {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    //    定义侧输出标签，用于重定向迟到数据
    val lateReadingsTag = new OutputTag[SensorReading]("late-readings")

    val dataStream = env.addSource(new SensorSource)
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(2)) {
        override def extractTimestamp(element: SensorReading): Long = element.timeStamp
      })
      .process(new FilterLateDataProcess)

    //    取出侧输出流
    val lateReadingsStream = dataStream.getSideOutput[SensorReading](lateReadingsTag)


    env.execute("LateDataProcessFunction")

  }

}

class FilterLateDataProcess extends ProcessFunction[SensorReading, SensorReading] {
  val lateReadingsTag = new OutputTag[SensorReading]("late-readings")

  //  对到来的每一条流
  override def processElement(value: SensorReading,
                              ctx: ProcessFunction[SensorReading, SensorReading]#Context,
                              out: Collector[SensorReading]): Unit = {
    //    判断该事件是不是迟到事件
    if (value.timeStamp < ctx.timerService().currentWatermark()) {
      ctx.output(lateReadingsTag, value)
    } else {
      out.collect(value)
    }
  }
}
