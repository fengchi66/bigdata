package Testing

import com.google.inject.matcher.Matchers
import org.apache.flink.api.common.functions.MapFunction

/**
 * @author wufc
 * @create 2020-04-05 12:13 上午
 */
/**
 * 对于这个无状态的MapFunction，如何通过传递合适的参数并验证输出？
 */
class IncrementMapFunction extends MapFunction[Long,Long]{
  override def map(value: Long): Long = {
    value + 1
  }
}

//class IncrementMapFunctionTest extends FlatSpec with Matchers{
//
//}
