package demo.transform

import org.apache.flink.streaming.api.scala._

/**
 * @author wufc
 * @create 2020-04-03 11:25 下午
 */
object Partitioning extends App {
  private val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

//  env.disableOperatorChaining() 在整个任务中禁用任务链:Operator chain

  env.socketTextStream("localhost",9999)
    .map((_,1)).setParallelism(4) //map算子的并行度为4
//    .partitionCustom(partitioner,0) 使用用户自定义的分区函数对每个元素选择目标分区
//    .shuffle 对元素随机分区
//    .rebalance //轮询分区，对每个分区创造相同的负载 在数据倾斜的情况下有用
//    .rescale //重新缩放，倍数关系 只需要本地传输数据，不需要网络传输数据
//    .broadcast() 将元素广播到每个分区
    .keyBy(_._1).setParallelism(2)  //keyBy算子的并行度为2

  /**
   * 1.开启新链接：从此运算符开始，开始新的链。这两个映射器将被链接，并且过滤器将不会链接到第一个映射器。
   *someStream.filter(...).map(...).startNewChain().map(...)
   * 2.禁用链接
   * 不要链接map
   * someStream.map(...).disableChaining()
   */

}
