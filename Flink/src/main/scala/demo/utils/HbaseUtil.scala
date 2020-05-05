package demo.utils

import org.apache.hadoop.hbase.HBaseConfiguration

/**
 * @author wufc
 * @create 2020-05-03 5:17 下午
 */
object HbaseUtil {

  def getHbaseConf() ={
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "localhost")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
    hbaseConf
  }

}
