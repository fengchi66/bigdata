## 术语

- 消息：Record。Kafka 是消息引擎嘛，这里的消息就是指 Kafka 处理的主要对象。
- 主题：Topic。主题是承载消息的逻辑容器，在实际使用中多用来区分具体的业务。
- 分区：Partition。一个有序不变的消息序列。每个主题下可以有多个分区。
- 消息位移：Offset。表示分区中每条消息的位置信息，是一个单调递增且不变的值。
- 副本：Replica。Kafka 中同一条消息能够被拷贝到多个地方以提供数据冗余，这些地方就是所谓的副本。副本还分为领导者副本和追随者副本，各自有不同的角色划分。副本是在分区层级下的，即每个分区可配置多个副本实现高可用。
- 生产者：Producer。向主题发布新消息的应用程序。
- 消费者：Consumer。从主题订阅新消息的应用程序。消费者位移：Consumer Offset。表征消费者消费进度，每个消费者都有自己的消费者位移。
- 消费者组：Consumer Group。多个消费者实例共同组成的一个组，同时消费多个分区以实现高吞吐。
- 重平衡：Rebalance。消费者组内某个消费者实例挂掉后，其他消费者实例自动重新分配订阅主题分区的过程。Rebalance 是 Kafka 消费者端实现高可用的重要手段。

### kafka消息三层架构

- 第一层是主题层，每个主题可以配置 M 个分区，而每个分区又可以配置 N 个副本。
- 第二层是分区层，每个分区的 N 个副本中只能有一个充当领导者角色，对外提供服务；其他 N-1 个副本是追随者副本，只是提供数据冗余之用。
- 第三层是消息层，分区中包含若干条消息，每条消息的位移从 0 开始，依次递增。
- 最后，**客户端程序只能与分区的领导者副本进行交互。**

### Kafka Broker 是如何持久化数据

- Kafka 使用**消息日志（Log）**来保存数据，一个日志就是磁盘上一个**只能追加写（Append-only）**消息的物理文件。因为只能追加写入，故避免了缓慢的随机 I/O 操作，改为性能较好的**顺序 I/O 写操作**

### Broker如何删除数据

- 通过**日志段（Log Segment）机制**。在 Kafka 底层，一个日志又进一步细分成多个日志段，消息被追加写到当前最新的日志段中，**当写满了一个日志段后，Kafka 会自动切分出一个新的日志段**，并将老的日志段封存起来。Kafka 在后台还有定时任务会定期地检查老的日志段是否能够被删除，从而实现回收磁盘空间的目的。

## kafka部署

![img](https://static001.geekbang.org/resource/image/ba/04/bacf5700e4b145328f4d977575f28904.jpg)

## kafka中的一些集群参数

- **log.dirs**：这是非常重要的参数，指定了 Broker 需要使用的若干个文件目录路径。要知道这个参数是没有默认值的，这说明什么？这说明它必须由你亲自指定。
- **zookeeper.connect**
- **auto.create.topics.enable**：是否允许自动创建 Topic。
- **unclean.leader.election.enable**：是否允许 Unclean Leader 选举。
- **auto.leader.rebalance.enable**：是否允许定期进行 Leader 选举。
- **log.retention.{hours|minutes|ms}**：这是个“三兄弟”，都是控制一条消息数据被保存多长时间。从优先级上来说 ms 设置最高、minutes 次之、hours 最低。
- **log.retention.bytes：**这是指定 Broker 为消息保存的总磁盘容量大小。
- **message.max.bytes**：控制 Broker 能够接收的最大消息大小。

## 生产者消息分区

- 分区的作用就是提供负载均衡的能力，或者说对数据进行分区的主要原因，就是为了实现系统的高伸缩性（Scalability）。
- **不同的分区能够被放置到不同节点的机器上，而数据的读写操作也都是针对分区这个粒度而进行的，这样每个节点的机器都能独立地执行各自分区的读写请求处理**。并且，我们还可以通过添加新的节点机器来增加整体系统的吞吐量。

### 分区策略

- **轮询策略**: 轮询策略有非常优秀的负载均衡表现，它总是能保证消息最大限度地被平均分配到所有分区上，故默认情况下它是最合理的分区策略，也是我们最常用的分区策略之一。
- **自定义分区key**

## 消息压缩

Producer 端压缩、Broker 端保持、Consumer 端解压缩。

```java

 Properties props = new Properties();
 props.put("bootstrap.servers", "localhost:9092");
 props.put("acks", "all");
 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 // 开启GZIP压缩
 props.put("compression.type", "gzip");
 
 Producer<String, String> producer = new KafkaProducer<>(props);
```

## 无消息丢失配置

**Kafka 只对“已提交”的消息（committed message）做有限度的持久化保证。**

- 已提交的消息：当 Kafka 的若干个 **Broker** 成功地接收到一条消息并写入到日志文件后，它们会**告诉生产者程序这条消息已成功提交**。此时，这条消息在 Kafka 看来就正式变为“已提交”消息了。

### 最佳实践

- 不要使用 producer.send(msg)，而要使用 producer.send(msg, callback)。记住，一定要使用带有回调通知的 send 方法。
- 设置 acks = all。acks 是 Producer 的一个参数，代表了你对“已提交”消息的定义。如果设置成 all，则表明所有副本 Broker 都要接收到消息，该消息才算是“已提交”。这是最高等级的“已提交”定义。
- 设置 retries 为一个较大的值。这里的 retries 同样是 Producer 的参数，对应前面提到的 Producer 自动重试。当出现网络的瞬时抖动时，消息发送可能会失败，此时配置了 retries > 0 的 Producer 能够自动重试消息发送，避免消息丢失。
- 设置 unclean.leader.election.enable = false。这是 Broker 端的参数，它控制的是哪些 Broker 有资格竞选分区的 Leader。如果一个 Broker 落后原先的 Leader 太多，那么它一旦成为新的 Leader，必然会造成消息的丢失。故一般都要将该参数设置成 false，即不允许这种情况的发生。
- 设置 replication.factor >= 3。这也是 Broker 端的参数。其实这里想表述的是，最好将消息多保存几份，毕竟目前防止消息丢失的主要机制就是冗余。
- 设置 min.insync.replicas > 1。这依然是 Broker 端参数，控制的是消息至少要被写入到多少个副本才算是“已提交”。设置成大于 1 可以提升消息持久性。在实际环境中千万不要使用默认值 1。确保 replication.factor > min.insync.replicas。如果两者相等，那么只要有一个副本挂机，整个分区就无法正常工作了。我们不仅要改善消息的持久性，防止数据丢失，还要在不降低可用性的基础上完成。
- 推荐设置成 replication.factor = min.insync.replicas + 1。确保消息消费完成再提交。Consumer 端有个参数 enable.auto.commit，最好把它设置成 false，并采用手动提交位移的方式。就像前面说的，这对于单 Consumer 多线程处理的场景而言是至关重要的。

## 幂等生产者和事务生产者

### 幂等性 Producer

```java
props.put(“enable.idempotence”, ture)，
或 
props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG， true)
```

- 它只能保证**单分区上的幂等**性，即一个幂等性 Producer 能够保证某个主题的一个分区上不出现重复消息，它无法实现多个分区的幂等性。
- 其次，它只能实现**单会话上的幂等性**，不能实现跨会话的幂等性。这里的会话，你可以理解为 Producer 进程的一次运行。当你重启了 Producer 进程之后，这种幂等性保证就丧失了。

### 事务型 Producer

- 事务型 Producer 能够保证将消息**原子性**地写入到多个分区中。这批消息要么全部写入成功，要么全部失败。另外，事务型 Producer 也不惧进程的重启。Producer 重启回来后，Kafka 依然保证它们发送消息的精确一次处理。有点像java中的锁，拿到锁 -> 处理业务代码 -> 释放锁
- 和幂等性 Producer 一样，开启 enable.idempotence = true。设置 Producer 端参数 transactional. id。最好为其设置一个有意义的名字。

```java

producer.initTransactions();
try {
            producer.beginTransaction();
            producer.send(record1);
            producer.send(record2);
            producer.commitTransaction();
} catch (KafkaException e) {
            producer.abortTransaction();
}
```

## Rebalance

Rebalance 发生的时机有三个：

- 组成员数量发生变化
- 订阅主题数量发生变化
- 订阅主题的分区数发生变化

### 避免Rebalance

#### 未能及时发送心跳

第一类非必要 Rebalance 是因为未能及时发送心跳，导致 Consumer 被“踢出”Group 而引发的。因此，你需要仔细地设置 session.timeout.ms 和 heartbeat.interval.ms 的值。我在这里给出一些推荐数值，你可以“无脑”地应用在你的生产环境中。

- 设置 **session.timeout.ms = 6s。**
- 设置 **heartbeat.interval.ms = 2s。**
- 要保证 Consumer 实例在被判定为“dead”之前，能够发送至少 3 轮的心跳请求，即 session.timeout.ms >= 3 * heartbeat.interval.ms。

#### Consumer 消费时间过长

- 第二类非必要 Rebalance 是 Consumer 消费时间过长导致的。我之前有一个客户，在他们的场景中，Consumer 消费数据时需要将消息处理之后写入到 MongoDB。显然，这是一个很重的消费逻辑。MongoDB 的一丁点不稳定都会导致 Consumer 程序消费时长的增加。
- 此时，**max.poll.interval.m**s 参数值的设置显得尤为关键。如果要避免非预期的 Rebalance，你最好将该参数值设置得大一点，比你的下游最大处理时间稍长一点。就拿 MongoDB 这个例子来说，如果写 MongoDB 的最长时间是 7 分钟，那么你可以将该参数设置为 8 分钟左右。

#### GC

如果你按照上面的推荐数值恰当地设置了这几个参数，却发现还是出现了 Rebalance，那么我建议你去排查一下 Consumer 端的 GC 表现，比如是否出现了频繁的 Full GC 导致的长时间停顿，从而引发了 Rebalance。为什么特意说 GC？那是因为在实际场景中，我见过太多因为 GC 设置不合理导致程序频发 Full GC 而引发的非预期 Rebalance 了。

![img](https://static001.geekbang.org/resource/image/32/d3/321c73b51f5e5c3124765101edc53ed3.jpg)

## 位移提交

- Kafka Consumer 的位移提交，是实现 Consumer 端语义保障的重要手段。
- 位移提交分为自动提交和手动提交，而手动提交又分为同步提交和异步提交。在实际使用过程中，推荐你使用手动提交机制，因为它更加可控，也更加灵活。
- 另外，建议你同时采用同步提交和异步提交两种方式，这样既不影响 TPS，又支持自动重试，改善 Consumer 应用的高可用性
- 要注意提交位移时可能的数据丢失或重复消费的问题。

## CommitFailedException

- 当**消息处理的总时间超过预设的 max.poll.interval.ms 参数值**时，Kafka Consumer 端会抛出 CommitFailedException 异常

### 解决办法

- 缩短单条消息处理的时间
- 增加 Consumer 端允许下游系统消费一批消息的最大时长
- 减少下游系统一次性消费的消息总数
- 下游系统使用多线程来加速消费

## 消费者组消费进度监控

Kafka JMX 监控指标

## 副本机制

