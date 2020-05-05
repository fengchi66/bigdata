package demo.state

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.state.{StateTtlConfig, ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.time.Time
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-04-03 5:02 下午
 *         实现一个计数状态，一旦计数达到2，它将发出平均值并清除状态
 */
object ExampleCountWindowAverage {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.fromCollection(List(
      (1L, 3L),
      (1L, 5L),
      (1L, 7L),
      (1L, 4L),
      (1L, 2L)
    )).keyBy(_._1)
      .flatMap(new CountWindowAverage())
      .print()

    env.execute("ExampleCountWindowAverage")
  }

}

class CountWindowAverage() extends RichFlatMapFunction[(Long, Long), (Long, Double)] {

  /**
   * 目前 State TTL 仅对 Processing Time 时间模式有效
   */
  //  TTL设置
  private val ttlConfig: StateTtlConfig = StateTtlConfig
    .newBuilder(Time.hours(1)) //状态的TTL时间,一旦设置了 TTL，那么如果上次访问的时间戳 + TTL 超过了当前时间，则表明状态过期了
    .cleanupInRocksdbCompactFilter() //RocksDB state backend的持续清理，每次compact的时候清理。
    .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite) // 表示状态时间戳的更新时期，如果设置为 Disabled，则表明不更新时间戳；如果设置为 OnCreateAndWrite，
    // 则表明当状态创建或每次写入时都会更新时间戳；如果设置为 OnReadAndWrite，则除了在状态创建和写入时更新时间戳外，读取也会更新状态的时间戳。
    .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired) //表示对已过期还未清除掉的状态如何处理
    //    ReturnExpiredIfNotCleanedUp，那么即使这个状态的时间戳表明它已经过期了，但是只要还未被真正清理掉，就会被返回给调用方；
    //    如果设置为 NeverReturnExpired，那么一旦这个状态过期了，那么永远不会被返回给调用方，只会返回空状态，避免了过期状态带来的干扰。
    .build()

  private var sumState: ValueState[(Long, Long)] = _

  override def open(parameters: Configuration): Unit = {
    //    状态描述器
    val sumStateDec = new ValueStateDescriptor[(Long, Long)]("sum-state", classOf[(Long, Long)])
    sumStateDec.enableTimeToLive(ttlConfig)

    sumState = getRuntimeContext.getState(sumStateDec)
  }

  override def flatMap(in: (Long, Long),
                       out: Collector[(Long, Double)]): Unit = {
    //    取出状态并初始化值
    var tupleValue = if (sumState.value() != null) sumState.value() else (0L, 0L)

    //    对每一条来的数据,保存到状态中(值，次数)
    tupleValue = (tupleValue._1 + in._2, tupleValue._2 + 1)

    //    更新状态
    sumState.update(tupleValue)
    //    计数达到2,发出平均值，并清除状态，重新开始
    if (tupleValue._2 >= 2L) {
      out.collect((in._1, tupleValue._1 / tupleValue._2))
      sumState.clear()
    }
  }

}
