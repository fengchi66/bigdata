package demo.AsyncIo

import java.beans.Transient
import java.sql.{DriverManager, PreparedStatement}
import java.util.concurrent.TimeUnit

import demo.bean.{OrderEvent, OrderFullEvent}
import demo.utils.{DateUtils, HbaseUtil}
import org.apache.flink.configuration
import org.apache.flink.runtime.concurrent.Executors
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.async.{AsyncFunction, ResultFuture, RichAsyncFunction}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.client.{Get, HTable}
import org.apache.hadoop.hbase.util.Bytes

import scala.concurrent.{ExecutionContext, Future}

/**
 * @author wufc
 * @create 2020-05-03 2:18 下午
 */
object AsyncMysqlRequest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val orderEventStream = env.fromCollection(List(
      OrderEvent(1L, 121, DateUtils.getTime("2020-05-03 12:01")),
      OrderEvent(2L, 122, DateUtils.getTime("2020-05-03 12:02")),
      OrderEvent(3L, 123, DateUtils.getTime("2020-05-03 12:03")),
      OrderEvent(4L, 124, DateUtils.getTime("2020-05-03 12:04")),
      OrderEvent(5L, 125, DateUtils.getTime("2020-05-03 12:05")),
      OrderEvent(6L, 126, DateUtils.getTime("2020-05-03 12:06")),
      OrderEvent(7L, 127, DateUtils.getTime("2020-05-03 12:07"))
    ))
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[OrderEvent](Time.seconds(5)) {
        override def extractTimestamp(element: OrderEvent): Long = element.timeStamp
      })

    AsyncDataStream.unorderedWait(
      orderEventStream,
      new AsyncFunctionForHBase,
      10000,
      TimeUnit.MICROSECONDS,
      100).print()


    env.execute("Test")
  }

}

class AsyncFunctionForMysql extends RichAsyncFunction[OrderEvent, OrderFullEvent] {

  var ps: java.sql.PreparedStatement = _
  val username = "root"
  val password = "992318ab"
  val drivername = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/flink?useUnicode=true&characterEncoding=UTF-8"
  Class.forName(drivername)

  // 可以异步请求的特定数据库客户端：mysql客户端
  private lazy val connection = DriverManager.getConnection(url, username, password)

  // 用于 future 回调的上下文环境（当前线程）
  implicit lazy val executor: ExecutionContext = ExecutionContext.fromExecutor(Executors.directExecutor())

  override def asyncInvoke(input: OrderEvent,
                           resultFuture: ResultFuture[OrderFullEvent]): Unit = {
    //    发送异步请求，接收 future 结果
    val resultFutureRequested: Future[OrderFullEvent] = Future {
      ps = connection.prepareStatement(s"select name,age from user_info where userId = ${input.userId} limit 1")
      val rs = ps.executeQuery()

      var name: String = ""
      var age: Int = 0

      while (rs.next()) {
        name = rs.getString("name")
        age = rs.getInt("age")
      }

      OrderFullEvent(input.orderId, input.userId, name, age, input.timeStamp)
    }

    // 回调函数，将结果发给future
    resultFutureRequested.onSuccess {
      case result: OrderFullEvent =>
        resultFuture.complete(Iterable(result))
    }

  }

  override def close(): Unit = {
    if (connection != null)
      connection.close()
  }
}

/**
 * 不可行
 */
class AsyncFunctionForHBase extends RichAsyncFunction[OrderEvent, OrderFullEvent] {

  private var hbaseConf: Configuration = _
  private var hTable: HTable = _

  // 用于 future 回调的上下文环境
  implicit lazy val executor: ExecutionContext = ExecutionContext.fromExecutor(Executors.directExecutor())

  override def open(parameters: configuration.Configuration): Unit = {
    hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "localhost")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")

    hTable = new HTable(hbaseConf, "user_info")
  }

  override def asyncInvoke(input: OrderEvent, resultFuture: ResultFuture[OrderFullEvent]): Unit = {
    val resultFutureRequested = Future {
      val get = new Get(Bytes.toBytes(input.userId))
      get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"))
      get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"))
      val result = hTable.get(get)

      val name = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")))
      val age = Bytes.toInt(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age")))

      OrderFullEvent(input.orderId, input.userId, name, age, input.timeStamp)
    }

    // 回调函数，将结果发给future
    resultFutureRequested.onSuccess {
      case result: OrderFullEvent =>
        resultFuture.complete(Iterable(result))
    }
  }

  override def close(): Unit = {
    if (hTable != null)
      hTable.close()
  }

}


