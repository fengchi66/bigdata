import demo.bean.SensorReading
import demo.utils.SensorSource
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author wufc
 * @create 2020-04-23 8:17 下午
 */
object Test02 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    env.addSource(new SensorSource)
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(5)) {
        override def extractTimestamp(element: SensorReading): Long = element.timeStamp
      })
      .map(data=>{
        (data.id,data.temperature)
      })
      .print()

    env.execute("Test02")
  }

}
