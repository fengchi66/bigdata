package demo.metrics

import demo.utils.WordSource
import org.apache.flink.streaming.api.scala._

/**
 * @author wufc
 * @create 2020-04-09 4:04 下午
 */
object Test {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    env.addSource(new WordSource)
      .print()

    env.execute("Test")
  }

}
