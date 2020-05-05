package demo.metrics

import demo.utils.WordSource
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.configuration.Configuration
import org.apache.flink.metrics.{Counter, SimpleCounter}
import org.apache.flink.streaming.api.scala._

/**
 * @author wufc
 * @create 2020-04-09 11:31 下午
 */
object CustomConterMetrics {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(2)

    env.addSource(new WordSource)
      .map(new MyMapper)
      .print()

    env.execute("CustomConterMetrics")
  }

}

class MyMapper extends RichMapFunction[String,String]{
  private var counter1: Counter = _
  private var counter2: Counter = _
  private var counter3: Counter = _
  private var index: Int = _

  override def open(parameters: Configuration): Unit = {
    index = getRuntimeContext.getIndexOfThisSubtask
    counter1 = getRuntimeContext.getMetricGroup
      .addGroup("flink-metrics-test")
      .counter("mapTest" + index)

    counter2 = getRuntimeContext.getMetricGroup
        .addGroup("flink-metrics-test")
        .counter("filterTest" + index)

    counter3 = getRuntimeContext.getMetricGroup
        .addGroup("flink-metrics-test")
        .counter("mapCounter",new SimpleCounter)
  }

  override def map(value: String): String = {
    println("index = " + (index + 1) + " count1 = " + counter1.getCount + " counter2 = " + counter2.getCount)
    counter1.inc()
    counter3.inc()
    if ("50".equals(value) || "20".equals(value)){
      counter2.inc()
    }
    value
  }
}
