package Testing

import org.apache.flink.streaming.api.scala._

/**
 * @author wufc
 * @create 2020-04-05 11:37 上午
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.socketTextStream("localhost",9999)
      .map(line=>(line,1)).uid("a")
      .keyBy(_._1)
      .sum(1)
  }

}
