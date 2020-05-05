package demo.state

import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
import org.apache.flink.runtime.state.{FunctionInitializationContext, FunctionSnapshotContext}
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction
import org.apache.flink.streaming.api.functions.sink.SinkFunction

import scala.collection.mutable.ListBuffer

/**
 * @author wufc
 * @create 2020-04-03 10:07 下午
 *         算子状态的应用
 *         该状态SinkFunction用于CheckpointedFunction 在将元素发送到外界之前先对其进行缓冲
 */
class BufferingSink(threshold: Int = 0) extends SinkFunction[(String, Int)] with CheckpointedFunction {

  private var checkpointedState: ListState[(String, Int)] = _

  private val bufferedElements = ListBuffer[(String, Int)]()

  override def invoke(value: (String, Int), context: SinkFunction.Context[_]): Unit = {
    bufferedElements += value
    if (bufferedElements.size == threshold) {
      for (element <- bufferedElements) {
        // send it to the sink
      }
      bufferedElements.clear()
    }
  }

  //  当每次任务触发checkpoint时执行，更新保存状态数据
  override def snapshotState(context: FunctionSnapshotContext): Unit = {
    checkpointedState.clear()
    for (element <- bufferedElements) {
      checkpointedState.add(element)
    }
  }

  //  初始化checkpoint 存储结构，一般在这里我们会实现两个逻辑：
  //1.判断checkpoint 是否是重启状态恢复，并实现状态恢复逻辑
  //2.初始化checkpoint存储逻辑规则。
  override def initializeState(context: FunctionInitializationContext): Unit = {
    val descriptor = new ListStateDescriptor[(String, Int)](
      "buffered-elements",
      TypeInformation.of(new TypeHint[(String, Int)]() {})
    )

    checkpointedState = context.getOperatorStateStore.getListState(descriptor)

    import scala.collection.JavaConversions._
    if (context.isRestored) {
      for (element <- checkpointedState.get()) {
        bufferedElements += element
      }
    }

  }
}
