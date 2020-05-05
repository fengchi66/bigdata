package demo.state

import demo.utils.FlinkUtils
import org.apache.flink.streaming.api.scala._

/**
 * @author wufc
 * @create 2020-04-05 11:28 上午
 */
object SavePointDemo {
  def main(args: Array[String]): Unit = {
    val env = FlinkUtils.getStreamEnv()

    env.addSource(FlinkUtils.getFlinkKafkaConsumer()).uid("source-id")
      .map(line=>(line,1)).setParallelism(4).uid("map-id")
      .keyBy(_._1)  //不能为shuffle制定uid
      .sum(1).setParallelism(2).uid("sum-id")
      .print().setParallelism(1)

    env.execute("SavePointDemo")

  }

}
