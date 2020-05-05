package demo.utils

import java.util.Properties

import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.configuration.Configuration
import org.apache.flink.contrib.streaming.state.{ConfigurableOptionsFactory, OptionsFactory, RocksDBStateBackend}
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.rocksdb.{BlockBasedTableConfig, ColumnFamilyOptions, DBOptions}

/**
 * @author wufc
 * @create 2020-03-31 6:41 下午
 */
object FlinkUtils {

  def getFlinkKafkaConsumer() = {
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "consumer-group")
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    // 如果没有记录偏移量，第一次从最开始消费
    properties.setProperty("auto.offset.reset", "earliest")
    //    kafka的消费者不自动提交偏移量，而是交给Flink通过CkeckPointing管理偏移量
    properties.setProperty("enable.auto.commit", "false")

    val flinkKafkaConsumer = new FlinkKafkaConsumer011[String]("UserBehavior", new SimpleStringSchema(), properties)

//    //    在kafka内部针对每个分区生成watermark
//    flinkKafkaConsumer.assignTimestampsAndWatermarks(new AscendingTimestampExtractor[String] {
//      override def extractAscendingTimestamp(element: String): Long = {
//        val arr = element.split(",", -1)
//        arr(2).toLong //假设arr(2)为eventTime时间戳的字段
//      }
//    })

    flinkKafkaConsumer.setCommitOffsetsOnCheckpoints(false) //offset不提交到kafka

    flinkKafkaConsumer
  }

  def getStreamEnv() = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //    开启CheckPointing
    env.enableCheckpointing(120000) //ck间隔为2min
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE) //EXACTLY_ONCE语义
    env.getCheckpointConfig.setCheckpointTimeout(60000) //一般设置为ck间隔的一半，检查点必须在1分钟之内完成，否则会被丢弃:Checkpoint的超时时间
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(60000) //checkpoint最小间隔
    env.getCheckpointConfig.setFailOnCheckpointingErrors(false) // // 设置 checkpoint 失败时，任务不会 fail，该 checkpoint 会被丢弃
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1) //同一时间只允许进行一个检查点

    //    一旦Flink程序被cannel后，是否保留CheckPoint的数据,有2个参数可以选择
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)


    //    使用RocketsDB状态后端并启用增量检查点
    val hdfsPath = "hdfs://localhost:9000/flink/checkpoints"
    val rocksDBStateBackend: RocksDBStateBackend = new RocksDBStateBackend(hdfsPath, true)
//    rocksDBStateBackend.enableTtlCompactionFilter() // 是否启用压缩过滤器来清除带有 TTL 的状态
//    rocksDBStateBackend.setOptions(new MyOptionFactory)

    env.setStateBackend(rocksDBStateBackend)

    //    固定延时重启：重启次数和间隔时间
    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 5000))

    env

  }

}

class MyOptionsFactory extends ConfigurableOptionsFactory{
  val default_size: Long = 256 * 1024 * 1024L  //256MB
  var blockCatchSize = default_size
  override def configure(configuration: Configuration): OptionsFactory = {
    this.blockCatchSize = configuration.getLong("mycustom.rocksdb.block.catch.size",blockCatchSize)
    this
  }

  override def createDBOptions(currentOptions: DBOptions): DBOptions = {
    currentOptions.setIncreaseParallelism(4).setUseFsync(false)
  }

  override def createColumnOptions(currentOptions: ColumnFamilyOptions): ColumnFamilyOptions = {
    currentOptions.setTableFormatConfig(
      new BlockBasedTableConfig().setBlockCacheSize(blockCatchSize)
        .setBlockSize(128 * 1024)  //128KB
    )
  }
}
