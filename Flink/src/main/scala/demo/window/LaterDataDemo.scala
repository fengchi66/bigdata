package demo.window

import java.lang

import demo.bean.SensorReading
import demo.utils.SensorSource
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-04-04 12:32 上午
 *         在窗口算子中为迟到时间定义侧输出
 */
object LaterDataDemo extends App {
  private val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
  env.setParallelism(1)

  //  定义侧输出标签，放迟到数据
  private val lateReadingsTag = new OutputTag[SensorReading]("late-readings")

  val resultStream: DataStream[(String, Int,Long)] = env.socketTextStream("localhost",9999)
    .map(line=>{
      val arr = line.split(",", -1)
      SensorReading(arr(0),arr(1).toLong,arr(2).toDouble)
    })
    .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(2)) {
      override def extractTimestamp(element: SensorReading): Long = element.timeStamp
    })
    .keyBy(_.id)
    .timeWindow(Time.seconds(15))
    .sideOutputLateData(lateReadingsTag) // 将迟到数据放入侧输出
    .process(new CountFunction) //计算每个窗口的读数个数

  //  从侧输出中获取迟到事件的数据流
  private val lateSensorReading: DataStream[SensorReading] = resultStream.getSideOutput[SensorReading](lateReadingsTag)

  resultStream.print()
  lateSensorReading.print()


  env.execute("LaterDataDemo")


}

class CountFunction extends ProcessWindowFunction[SensorReading, (String, Int,Long), String, TimeWindow] {
  override def process(key: String,
                       context: Context,
                       elements: Iterable[SensorReading],
                       out: Collector[(String, Int,Long)]): Unit = {
    val count = elements.size
    out.collect((key, count,context.window.getEnd))
  }
}


