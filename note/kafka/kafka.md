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

