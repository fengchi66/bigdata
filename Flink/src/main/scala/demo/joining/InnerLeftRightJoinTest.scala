package demo.joining

import java.lang

import demo.bean.{OrderLogEvent1, OrderLogEvent2, OrderResultEvent}
import demo.utils.DateUtils
import org.apache.flink.api.common.functions.{CoGroupFunction, JoinFunction}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author wufc
 * @create 2020-04-29 11:54 下午
 */
object InnerLeftRightJoinTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    val leftOrderStream = env.fromCollection(List(
      OrderLogEvent1(1L, 22.1, DateUtils.getTime("2020-04-29 13:01")),
      OrderLogEvent1(2L, 22.2, DateUtils.getTime("2020-04-29 13:03")),
      OrderLogEvent1(4L, 22.3, DateUtils.getTime("2020-04-29 13:04")),
      OrderLogEvent1(4L, 22.4, DateUtils.getTime("2020-04-29 13:05")),
      OrderLogEvent1(5L, 22.5, DateUtils.getTime("2020-04-29 13:07")),
      OrderLogEvent1(6L, 22.6, DateUtils.getTime("2020-04-29 13:09"))
    ))
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[OrderLogEvent1](Time.seconds(5)) {
        override def extractTimestamp(element: OrderLogEvent1): Long = element.timeStamp
      })
      .keyBy(_.orderId)

    val rightOrderStream = env.fromCollection(List(
      OrderLogEvent2(1L, 121, DateUtils.getTime("2020-04-29 13:01")),
      OrderLogEvent2(2L, 122, DateUtils.getTime("2020-04-29 13:03")),
      OrderLogEvent2(3L, 123, DateUtils.getTime("2020-04-29 13:04")),
      OrderLogEvent2(4L, 124, DateUtils.getTime("2020-04-29 13:05")),
      OrderLogEvent2(5L, 125, DateUtils.getTime("2020-04-29 13:07")),
      OrderLogEvent2(7L, 126, DateUtils.getTime("2020-04-29 13:09"))
    ))
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[OrderLogEvent2](Time.seconds(5)) {
        override def extractTimestamp(element: OrderLogEvent2): Long = element.timeStamp
      })
      .keyBy(_.orderId)

//        leftOrderStream
//          .join(rightOrderStream)
//          .where(_.orderId)
//          .equalTo(_.orderId)
//          .window(TumblingEventTimeWindows.of(Time.minutes(5))) // 5min的时间滚动窗口
//          .apply(new InnerWindowJoinFunction)
//          .print()

    val coGroupedStream = leftOrderStream
      .coGroup(rightOrderStream)
      .where(_.orderId)
      .equalTo(_.orderId)
      .window(TumblingEventTimeWindows.of(Time.minutes(5))) // 5min的时间滚动窗口

//    coGroupedStream.apply(new InnerWindowJoinFunction).print()
//    coGroupedStream.apply(new LeftWindowJoinFunction).print()
//    coGroupedStream.apply(new RightWindowJoinFunction).print()

    leftOrderStream
        .intervalJoin(rightOrderStream)
        .between(Time.minutes(-2),Time.minutes(1))
        .process(new IntervalJoinFunction)
        .print()

    env.execute("Job")
  }

}

//class InnerWindowJoinFunction extends JoinFunction[OrderLogEvent1, OrderLogEvent2, OrderResultEvent] {
//  override def join(first: OrderLogEvent1, second: OrderLogEvent2): OrderResultEvent = {
//    OrderResultEvent(first.orderId, first.amount, second.itemId)
//  }
//}

class InnerWindowJoinFunction extends CoGroupFunction[OrderLogEvent1,OrderLogEvent2,OrderResultEvent]{
  override def coGroup(first: java.lang.Iterable[OrderLogEvent1],
                       second: java.lang.Iterable[OrderLogEvent2],
                       out: Collector[OrderResultEvent]): Unit = {
    /**
     * 将Java的Iterable对象转化为Scala的Iterable对象
     */
    import scala.collection.JavaConverters._
    val scalaT1 = first.asScala.toList
    val scalaT2 = second.asScala.toList

    // inner join要比较的是同一个key下，同一个时间窗口内的数据
    if (scalaT1.nonEmpty && scalaT1.nonEmpty){
      for (left <- scalaT1) {
        for (right <- scalaT2) {
          out.collect(OrderResultEvent(left.orderId,left.amount,right.itemId))
        }
      }
    }

  }
}

class LeftWindowJoinFunction extends CoGroupFunction[OrderLogEvent1,OrderLogEvent2,OrderResultEvent]{
  override def coGroup(first: lang.Iterable[OrderLogEvent1],
                       second: lang.Iterable[OrderLogEvent2],
                       out: Collector[OrderResultEvent]): Unit = {
    /**
     * 将Java的Iterable对象转化为Scala的Iterable对象
     */
    import scala.collection.JavaConverters._
    val scalaT1 = first.asScala.toList
    val scalaT2 = second.asScala.toList

    for (left <- scalaT1) {
      var flag = false // 定义flag，left流中的key在right流中是否匹配
      for (right <- scalaT2) {
        out.collect(OrderResultEvent(left.orderId,left.amount,right.itemId))
        flag = true;
      }
      if (!flag){ // left流中的key在right流中没有匹配到，则给itemId输出默认值0L
        out.collect(OrderResultEvent(left.orderId,left.amount,0L))
      }
    }
  }
}

class RightWindowJoinFunction extends CoGroupFunction[OrderLogEvent1,OrderLogEvent2,OrderResultEvent]{
  override def coGroup(first: lang.Iterable[OrderLogEvent1],
                       second: lang.Iterable[OrderLogEvent2],
                       out: Collector[OrderResultEvent]): Unit = {
    /**
     * 将Java的Iterable对象转化为Scala的Iterable对象
     */
    import scala.collection.JavaConverters._
    val scalaT1 = first.asScala.toList
    val scalaT2 = second.asScala.toList

    for (right <- scalaT2) {
      var flag = false  // 定义flag，right流中的key在left流中是否匹配

      for (left <- scalaT1) {
        out.collect(OrderResultEvent(left.orderId,left.amount,right.itemId))
        flag = true
      }

      if (!flag){  //没有匹配到的情况
        out.collect(OrderResultEvent(right.orderId,0.00,right.itemId))
      }

    }
  }
}

class IntervalJoinFunction extends ProcessJoinFunction[OrderLogEvent1,OrderLogEvent2,OrderResultEvent]{
  override def processElement(left: OrderLogEvent1,
                              right: OrderLogEvent2,
                              ctx: ProcessJoinFunction[OrderLogEvent1, OrderLogEvent2, OrderResultEvent]#Context,
                              out: Collector[OrderResultEvent]): Unit = {
    out.collect(OrderResultEvent(left.orderId,left.amount,right.itemId))
  }
}


