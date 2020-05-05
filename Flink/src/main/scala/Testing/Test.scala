package Testing

import demo.utils.SensorSource
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._

/**
 * @author wufc
 * @create 2020-04-26 12:07 下午
 */
object Test {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    env.addSource(new SensorSource)
      .map(data=>{
        (data.id,data.temperature)
      })
      .print()

    
    env.execute("Job")
  }

}
