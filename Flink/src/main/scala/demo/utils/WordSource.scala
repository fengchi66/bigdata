package demo.utils

import org.apache.flink.streaming.api.functions.source.SourceFunction

/**
 * @author wufc
 * @create 2020-04-09 4:01 下午
 */
class WordSource extends SourceFunction[String]{
  private var isRunning = true
  override def run(ctx: SourceFunction.SourceContext[String]): Unit = {
    while (isRunning){
      ctx.collect(math.round(Math.random() * 100).toString)
      Thread.sleep(100)
    }
  }

  override def cancel(): Unit = {
    isRunning = false
  }
}
