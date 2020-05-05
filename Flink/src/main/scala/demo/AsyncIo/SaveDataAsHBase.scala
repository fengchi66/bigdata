package demo.AsyncIo

import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.client.{Get, HTable, Put}
import org.apache.hadoop.hbase.util.Bytes

/**
 * @author wufc
 * @create 2020-05-03 8:52 下午
 */
object SaveDataAsHBase {
  val hbaseConf = HBaseConfiguration.create()
  hbaseConf.set("hbase.zookeeper.quorum", "localhost")
  hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")

  def main(args: Array[String]): Unit = {
//    addRowDataString("user_info","121","info","name","spark")
//    addRowDataString("user_info","122","info","name","hbase")
//    addRowDataString("user_info","123","info","name","hadoop")
//    addRowDataString("user_info","124","info","name","kafka")
//    addRowDataString("user_info","125","info","name","hive")
//    addRowDataString("user_info","126","info","name","kylin")
//    addRowDataString("user_info","127","info","name","flume")
//
//    addRowDataInt("user_info","121","info","age",11)
//    addRowDataInt("user_info","122","info","age",12)
//    addRowDataInt("user_info","123","info","age",13)
//    addRowDataInt("user_info","124","info","age",14)
//    addRowDataInt("user_info","125","info","age",15)
//    addRowDataInt("user_info","126","info","age",16)
//    addRowDataInt("user_info","127","info","age",17)

//    getRow("user_info","121")
    getRowQualifier("user_info","121","info","name","age")



  }

  def addRowDataString(tableName:String,rowKey:String,cf:String,column:String,value:String): Unit ={
    val hTable = new HTable(hbaseConf, tableName)

    val put = new Put(Bytes.toBytes(rowKey))

    put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(value))

    hTable.put(put)

    hTable.close()

    println("插入hbase")

  }

  def addRowDataInt(tableName:String,rowKey:String,cf:String,column:String,value:Int): Unit ={
    val hTable = new HTable(hbaseConf, tableName)

    val put = new Put(Bytes.toBytes(rowKey))

    put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(value))

    hTable.put(put)

    hTable.close()
    println("插入hbase")


  }

  def getRow(tableName: String, rowKey: String): Unit = {
    val table = new HTable(hbaseConf, tableName)
    val get = new Get(Bytes.toBytes(rowKey))
    //get.setMaxVersions();显示所有版本
    //get.setTimeStamp();显示指定时间戳的版本
    val result = table.get(get)
    import scala.collection.JavaConversions._
    for (cell <- result.rawCells) {
      System.out.println("行键:" + Bytes.toString(result.getRow))
      System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)))
      System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)))
      System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)))
      System.out.println("时间戳:" + cell.getTimestamp)
    }
  }


  def getRowQualifier(tableName: String, rowKey: String, family: String, col1: String,col2:String): Unit = {
    val table = new HTable(hbaseConf, tableName)
    val get = new Get(Bytes.toBytes(rowKey))
    get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"))
    get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"))
    val result = table.get(get)

    val name = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")))
    val age = Bytes.toInt(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age")))

    println(name)
    println(age)


  }



}
