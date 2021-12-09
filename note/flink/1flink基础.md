## 名词

- **任务链**：Flink 将算子的 subtasks *链接*成 *tasks*。每个 task 由一个线程执行。将算子链接成 task 是个有用的优化：它减少线程间切换、缓冲的开销，并且减少延迟的同时增加整体吞吐量。
- **Flink检查点算法**：分布式快照算法，不暂停整个应用，会把生成检查点的过程与处理过程分离。

## Flink检查点

### Flink检查点流程生成流程

- JobManager向每个source任务发送一个新的检查点编号，以此启动检查点流程。
- **source任务**收到消息后，暂停发出记录，生成本地检查点到状态后端，并将检查点分隔符和检查点编号**广播**到下游分区。状态后端保存到检查点后，将JobManager确认消息。source任务之后继续发出记录。
- **barrier对齐**：当任务收到新的检查点分隔符时，会等待其他所有分区都收到这个barrier，在此期间接收到的新数据会缓存起来。
- 任务所有分区收到barrier后执行检查点生成流程，保存到状态后端，并将barrier广播到下游。
- **sink算子**收到barrier后，barrier对齐，将自身状态写入检查点。
- **发生故障时**：从上一次生成的检查点中恢复任务。

### 对性能的影响

- 任务在将其状态存入检查点的过程，处于阻塞状态。由于状态可能很大，写入远程文件系统可能很久。
- rocksdb异步增量检查点。
- 保存点：生成算法和保存点完全一样，由用户显式触发。

## 端对端一致性

- Flink的检查点和恢复机制会周期性的为应用状态创建一致性检查点，**一旦发生故障，应用会从最新的检查点恢复状态并继续处理数据**。一般数据源使用kafka，可重置offset。但**可重置的源与检查点机制，只能保证应用内部的精确一次保障，但无法提供端对端的一致性保障。**
- **幂等写**：在重放时可以覆盖之前写的结果。也就是sink端支持upsert功能。
- **事务写**：两阶段提交，需要sink端支持事务。**每次生成检查点，sink开启事务并将收到的全部记录加入到该事务**中，直到接到**检查点完成**的通知后，才会**提交事务写入结果**。

### flink两阶段提交(2PC)

- 预提交阶段：在预提交阶段，Sink 将要写入外部存储的数据保存到状态后端存储中，同时**以事务的方式将数据写入外部存储**，若预提交阶段一个算子出现异常，所有算子检查点必须被终止，Flink 回滚到最近完成的检查点

- 提交阶段：预提交阶段完成后，通知所有算子，确认 checkpoint 已成功完成，进入提交阶段；此时将预提交阶段开启的外部事务提交

- 在Flink中使用两阶段提交，需要实现TwoPhaseCommitSinkFunction这个抽象类的四个方法

  ```java
  protected abstract TXN beginTransaction() throws Exception;
  protected abstract void preCommit(TXN transaction) throws Exception; 
  protected abstract void commit(TXN transaction);
  protected abstract void abort(TXN transaction);
  ```

- **beginTransaction()**：开始一个事务，返回事务信息的句柄。

  **preCommit()**：预提交（即提交请求）阶段的逻辑。

  **commit()**：正式提交阶段的逻辑。

  **abort()**：取消事务。

  下面以Flink与Kafka的集成来说明2PC的具体流程。注意这里的Kafka版本必须是0.11及以上，因为只有0.11+的版本才支持幂等producer以及事务性，从而2PC才有存在的意义。

#### 开始事务

- 如果在Flink里面明确要求exactly once语义时，就会创建事务生产者并且启动事务。

  ```java
  @Override
  	protected FlinkKafkaProducer.KafkaTransactionState beginTransaction() throws FlinkKafkaException {
  		switch (semantic) {
  			case EXACTLY_ONCE:
  				FlinkKafkaInternalProducer<byte[], byte[]> producer = createTransactionalProducer();
  				producer.beginTransaction();
  				return new FlinkKafkaProducer.KafkaTransactionState(producer.getTransactionalId(), producer);
  			case AT_LEAST_ONCE:
  			case NONE:
  				// Do not create new producer on each beginTransaction() if it is not necessary
  				final FlinkKafkaProducer.KafkaTransactionState currentTransaction = currentTransaction();
  				if (currentTransaction != null && currentTransaction.producer != null) {
  					return new FlinkKafkaProducer.KafkaTransactionState(currentTransaction.producer);
  				}
  				return new FlinkKafkaProducer.KafkaTransactionState(initNonTransactionalProducer(true));
  			default:
  				throw new UnsupportedOperationException("Not implemented semantic");
  		}
  	}
  ```

#### 预提交阶段

- FlinkKafkaProducer.preCommit()方法的实现很简单。其中的flush()方法实际上是代理了KafkaProducer.flush()方法。

  ```java
  protected void preCommit(FlinkKafkaProducer.KafkaTransactionState transaction) throws FlinkKafkaException {
  		switch (semantic) {
  			case EXACTLY_ONCE:
  			case AT_LEAST_ONCE:
  				flush(transaction);
  				break;
  			case NONE:
  				break;
  			default:
  				throw new UnsupportedOperationException("Not implemented semantic");
  		}
  		checkErroneous();
  	}
  ```

- 那么preCommit()方法是在哪里使用的呢？答案是TwoPhaseCommitSinkFunction.snapshotState()方法。从前面的类图可以得知，TwoPhaseCommitSinkFunction也继承了CheckpointedFunction接口，所以2PC是与[检查点机制](https://www.zhihu.com/search?q=检查点机制&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"article"%2C"sourceId"%3A111304281})

  一同发挥作用的。

- **当屏障到达Kafka sink后，触发preCommit**(实际上是KafkaProducer.flush())方法刷写消息数据，但还未真正提交。接下来还是需要通过检查点来触发提交阶段。

#### 提交阶段

- FlinkKafkaProducer.commit()方法实际上是代理了KafkaProducer.commitTransaction()方法，正式向Kafka提交事务。

  ```java
  @Override
  	protected void commit(FlinkKafkaProducer.KafkaTransactionState transaction) {
  		if (transaction.isTransactional()) {
  			try {
  				transaction.producer.commitTransaction();
  			} finally {
  				recycleTransactionalProducer(transaction.producer);
  			}
  		}
  	}
  ```

- 该方法的调用点位于TwoPhaseCommitSinkFunction.notifyCheckpointComplete()方法中。顾名思义，**当所有检查点都成功完成之后，会回调这个方法**。

#### 回滚事务

- 可见，只有在所有检查点都成功完成这个前提下，写入才会成功。这符合前文所述2PC的流程，其中JobManager为协调者，各个算子为参与者（不过只有sink一个参与者会执行提交）。一旦有检查点失败，notifyCheckpointComplete()方法就不会执行。如果重试也不成功的话，最终会调用abort()方法回滚事务。

  ```java
  @Override
      protected void abort(KafkaTransactionState transaction) {
          if (transaction.isTransactional()) {
              transaction.producer.abortTransaction();
              recycleTransactionalProducer(transaction.producer);
          }
      }
  ```

  
