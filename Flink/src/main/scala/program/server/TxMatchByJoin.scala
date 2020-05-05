package program.server

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import program.bean.{OrderEventLog, ReceiptEvent}

/**
 * @author wufc
 * @create 2020-04-23 2:14 下午
 *         双流join :intervalJoin
 */
object TxMatchByJoin {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    //    读取订单事件流
    val orderEventStream = env.readTextFile("Flink/Data/OrderLog.csv")
      .map(line => {
        val arr = line.split(",", -1)
        OrderEventLog(arr(0).trim, arr(1).trim, arr(2).trim, arr(3).trim.toLong * 1000L)
      })
      .filter(_.txId != "") // 交易id不为空
      .assignAscendingTimestamps(_.eventTime)
      .keyBy(_.txId) //以交易id分组

    //    加载交易数据
    val receiptEventStream = env.readTextFile("Flink/Data/ReceiptLog.csv")
      .map(line => {
        val arr = line.split(",", -1)
        ReceiptEvent(arr(0).trim, arr(1).trim, arr(2).trim.toLong * 1000L)
      })
      .assignAscendingTimestamps(_.eventTime)
      .keyBy(_.txId) //以交易id分组

    orderEventStream
      .intervalJoin(receiptEventStream)
      .between(Time.seconds(-5), Time.seconds(5))
      .process(new TxPayMatchByJoin)
      .print()

    env.execute("TxMatchByJoin")
  }
}

class TxPayMatchByJoin extends ProcessJoinFunction[OrderEventLog, ReceiptEvent, (OrderEventLog, ReceiptEvent)] {
  override def processElement(left: OrderEventLog,
                              right: ReceiptEvent,
                              ctx: ProcessJoinFunction[OrderEventLog, ReceiptEvent, (OrderEventLog, ReceiptEvent)]#Context,
                              out: Collector[(OrderEventLog, ReceiptEvent)]): Unit = {
    out.collect((left, right))
  }
}
