package program.server

import demo.utils.DateUtils
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.co.CoProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import program.bean.{OrderEventLog, ReceiptEvent}

/**
 * @author wufc
 * @create 2020-04-22 9:40 下午
 *         交易对账
 */
object TxMatchDetect {
  //  定义侧输出流tag
  //  在交易流中没有匹配到的订单流数据（有订单，但是没有交易）
  val unmatchedPays = new OutputTag[OrderEventLog]("unmatchedPays")
  //  在订单流中没有匹配到的交易流的数据（有交易，但是没有订单）
  val unmatchReceipts = new OutputTag[ReceiptEvent]("unmatchedReceipts")

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

    //    将两条流连接起来，共同处理
    orderEventStream.connect(receiptEventStream)
      .process(new TxPayMatch)
      .print()

    env.execute("Job")
  }

  class TxPayMatch extends CoProcessFunction[OrderEventLog, ReceiptEvent, (OrderEventLog, ReceiptEvent)] {
    // 定义状态来保存已经到达的订单支付事件和到账事件
    lazy val payState: ValueState[OrderEventLog] = getRuntimeContext.getState(
      new ValueStateDescriptor[OrderEventLog]("pay-state", classOf[OrderEventLog])
    )

    lazy val receiptState: ValueState[ReceiptEvent] = getRuntimeContext.getState(
      new ValueStateDescriptor[ReceiptEvent]("receipt-state", classOf[ReceiptEvent])
    )

    //    订单支付事件数据的处理
    override def processElement1(pay: OrderEventLog,
                                 ctx: CoProcessFunction[OrderEventLog, ReceiptEvent, (OrderEventLog, ReceiptEvent)]#Context,
                                 out: Collector[(OrderEventLog, ReceiptEvent)]): Unit = {
      //      判断有没有到账信息
      val receipt = receiptState.value()

      if (receipt != null) {
        // 如果已经有receipt，在主流中输出匹配信息,清空状态
        out.collect((pay, receipt))
        receiptState.clear()
      } else {
        //        如果还没到，把pay存入状态,并且注册一个定时器等待
        payState.update(pay)
        ctx.timerService().registerEventTimeTimer(pay.eventTime + 5000L)
      }


    }

    //    到账事件数据的处理
    override def processElement2(receipt: ReceiptEvent,
                                 ctx: CoProcessFunction[OrderEventLog, ReceiptEvent, (OrderEventLog, ReceiptEvent)]#Context,
                                 out: Collector[(OrderEventLog, ReceiptEvent)]): Unit = {
      // 同样的处理流程:判断有没有
      val pay = payState.value()

      if (pay != null) {
        // 如果已经有pay，在主流中输出匹配信息，清空状态
        out.collect((pay, receipt))
        payState.clear()
      } else {
        //        如果还没到，把receipt存入状态，并且注册一个定时器等待
        ctx.timerService().registerEventTimeTimer(receipt.eventTime + 5000L)
      }

    }

//    定时器处触发的时候
    override def onTimer(timestamp: Long,
                         ctx: CoProcessFunction[OrderEventLog, ReceiptEvent, (OrderEventLog, ReceiptEvent)]#OnTimerContext,
                         out: Collector[(OrderEventLog, ReceiptEvent)]): Unit = {
//      到时间了，如果还没有收到某个事件，那么输出报警信息
      if (payState.value() != null){
//        证明没有匹配到receipt,将pay输出到侧输出流
        ctx.output(unmatchedPays,payState.value())
      }
      if (receiptState.value() != null){
//        证明没有匹配到pay，将receipt输出到测输出流
        ctx.output(unmatchReceipts,receiptState.value())
      }

      payState.clear()
      receiptState.clear()

    }
  }

}
