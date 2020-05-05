package program.server

import java.util

import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.{CEP, PatternStream}
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import program.bean.LoginEvent

/**
 * @author wufc
 * @create 2020-04-01 9:29 下午
 */
object LoginFailWithCep {
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
      .keyBy(_.userId)

    //    定义匹配模式
    val loginFailPattern = Pattern
      .begin[LoginEvent]("begin")
      .where(_.eventType == "fail")
      .next("next")
      .where(_.eventType == "fail")
      .within(Time.seconds(2))

    //    在数据流中匹配出定义好的模式
    val patternStream: PatternStream[LoginEvent] = CEP.pattern(dataSoueceStream, loginFailPattern)

    //      .select方法传入一个 pattern select function，当检测到定义好的模式序列时就会调用
    val loginFailDataStream = patternStream.select(new LoginFailMatch)

    loginFailDataStream.print()

    env.execute("LoginFailWithCep")
  }

}

class LoginFailMatch extends PatternSelectFunction[LoginEvent,Long]{
  override def select(map: util.Map[String, util.List[LoginEvent]]): Long = {
    val first = map.getOrDefault("begin", null).iterator().next()
    val next = map.getOrDefault("next", null).iterator().next()
    first.userId
    next.userId
  }
}
