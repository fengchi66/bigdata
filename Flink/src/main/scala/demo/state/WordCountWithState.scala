package demo.state

import org.apache.flink.streaming.api.scala._

/**
 * @author wufc
 * @create 2020-04-03 6:57 下午
 */
object WordCountWithState extends App{
  private val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

  env.socketTextStream("localhost",9999)
    .map((_,1))
    .keyBy(_._1)
    .mapWithState((in: (String,Int), count: Option[Int]) =>
    count match {
      case Some(c) => ((in._1,c),Some(c + in._2))
      case None => ((in._1,1),Some(in._2))
    }
    ).print()

  env.execute("WordCountWithState")

}
