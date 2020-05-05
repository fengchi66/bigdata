package program.server

import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector
import program.bean.LoginEvent

import scala.collection.mutable.ListBuffer

/**
 * @author wufc
 * @create 2020-04-01 5:10 下午
 *         同一用户在2秒内连续2次登陆失败，发出报警信息
 */
object LoginFail {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(5000) //每5秒生成一次watermark
    env.setParallelism(1)

    val dataSoueceStream = env.readTextFile("Flink/Data/LoginLog.csv")
      .map(line => {
        val arr = line.split(",", -1)
        LoginEvent(arr(0).trim.toLong, arr(1), arr(2), arr(3).toLong)
      })
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[LoginEvent](Time.seconds(2)) {
        override def extractTimestamp(t: LoginEvent): Long = t.eventTime * 1000L
      })

    dataSoueceStream
      .keyBy(_.userId)
      .process(new LoginFailProcessFunction)
      .print()

    env.execute("LoginFail")
  }

}

class LoginFailProcessFunction extends KeyedProcessFunction[Long, LoginEvent, Long] {
  //  定义状态，存放登陆失败日志
  private var loginState: ListState[LoginEvent] = _

  override def open(parameters: Configuration): Unit = {
    val loginStateDec = new ListStateDescriptor[LoginEvent]("login-state", classOf[LoginEvent])
    loginState = getRuntimeContext.getListState(loginStateDec)
  }

  override def processElement(i: LoginEvent,
                              context: KeyedProcessFunction[Long, LoginEvent, Long]#Context,
                              collector: Collector[Long]): Unit = {
    //    如果事件类型为fail，放入状态中
    if (i.eventType == "fail")
      loginState.add(i)

    //    注册定时器,触发事件定在2S后
    context.timerService().registerEventTimeTimer(i.eventTime * 1000 + 2000L)
  }

  override def onTimer(timestamp: Long,
                       ctx: KeyedProcessFunction[Long, LoginEvent, Long]#OnTimerContext,
                       out: Collector[Long]): Unit = {
    //    取出状态中数据
    val allLoginEvent = new ListBuffer[LoginEvent]
    import scala.collection.JavaConversions._

    for (login <- loginState.get()) {
      allLoginEvent += login
    }

    //    清除状态
    loginState.clear()

    if (allLoginEvent.length > 1)
      out.collect(allLoginEvent.head.userId)

  }
}