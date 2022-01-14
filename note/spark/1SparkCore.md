## RDD编程模型

### MapReduce编程模型

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642055930825-df893709-a0a6-43c7-bd26-9f0702c334c3.png)

### DAG编程模型 ![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642056003153-8e80f89f-dfa8-40e7-a664-eaabf4758484.png)

### RDD是什么

Resilient Distributed Datasets 弹性分布式数据集

- **Resilient**： 弹性的、可容错的，一种容错的内存计算数据抽象
- **Distributed**：通过多个分区将数据分散在不同节点

- **Datasets**：数据集：**一种数据抽象集合**

**物理上：**

- RDD是一组记录的集合。  
- 一个RDD可以分成多个分区， 每个分区是不可变的，分散在 集群的各个地方

**逻辑上：**

- RDD是一个编程的数据抽象， 可以对它进行各自操作。 
- RDD操作都是高阶函数，这些 操作内部都是并发执行 

- 由两种类型的操作: 转换和执 行。

### RDD操作

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642062834701-3fe73271-ebc3-4ca5-b5ae-f462196a5608.png)

### RDD依赖

- **窄依赖**：父RDD和子RDDpartition之间的关系是一对一的，或者父RDD一个partition只对应一个子RDD的partition情况下的父RDD和子RDD partition关系是多对一的，**不会有shuffle产生。**父RDD的一个分区去到了子RDD的一个分区
- **宽依赖**：父RDD与子RDD partition之间的关系是一对多，**会有shuffle的产生**。父RDD的一个分区的数据去到了子RDD的不同分区里面。

- 宽依赖会将pipeline划分为不同步stage，放在不同的task上去执行

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642063176925-599d90ab-937f-49e9-84e8-de587f23623d.png)

### RDD容错

当pipeline中其中的task出现故障挂机的时候，RDD可以自动恢复的能力。依赖的是RDD中的依赖(血缘)关系。

如RDD2中的Partition3发生故障后，可以基于依赖关系，由RDD的Partiton3重新计算。

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642063412621-d7843be8-df14-4ef4-ad57-e71bd1155a01.png)

## Spark Core架构和原理

### Spark运行架构

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642066076788-d4dd09cb-890a-4cbe-a5cd-650a6e546de9.png)

### Job/Stage/Task

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642066129894-3fd8c8d5-c697-44a4-86ae-68f2becef9c0.png)

### 任务提交流程

[spark任务提交方式和执行流程](https://zhuanlan.zhihu.com/p/61597611)

- Application：表示你的应用程序
- Driver：表示main()函数，创建SparkContext。由SparkContext负责与ClusterManager通信，进行资源的申请，任务的分配和监控等。程序执行完毕后关闭SparkContext

- Executor：某个Application运行在Worker节点上的一个进程，该进程负责运行某些task，并且负责将数据存在内存或者磁盘上。在Spark on Yarn模式下，其进程名称为 CoarseGrainedExecutor Backend，一个CoarseGrainedExecutor Backend进程有且仅有一个executor对象，它负责将Task包装成taskRunner，并从线程池中抽取出一个空闲线程运行Task，这样，每个CoarseGrainedExecutorBackend能并行运行Task的数据就取决于分配给它的CPU的个数。
- Worker：集群中可以运行Application代码的节点。在Standalone模式中指的是通过slave文件配置的worker节点，在Spark on Yarn模式中指的就是NodeManager节点。

- Task：在Executor进程中执行任务的工作单元，多个Task组成一个Stage
- Job：包含多个Task组成的并行计算，是由Action行为触发的

- Stage：每个Job会被拆分很多组Task，作为一个TaskSet，其名称为Stage
- DAGScheduler：根据Job构建基于Stage的DAG，并提交Stage给TaskScheduler，其划分Stage的依据是RDD之间的依赖关系

- TaskScheduler：将TaskSet提交给Worker（集群）运行，每个Executor运行什么Task就是在此处分配的。

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642066218105-29124441-856e-4ec5-a122-d14b2fc7c9df.png)

![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642066248077-d5f1d27b-f975-40de-8704-6fb2746e0b50.png)

#### 流程

- 构建Spark Application的运行环境（启动SparkContext），SparkContext向资源管理器（可以是Standalone、Mesos或YARN）注册并申请运行Executor资源；
- 资源管理器分配Executor资源并启动StandaloneExecutorBackend，Executor运行情况将随着心跳发送到资源管理器上；

- SparkContext构建成DAG图，将DAG图分解成Stage，并把Taskset发送给Task Scheduler。Executor向SparkContext申请Task
- Task Scheduler将Task发放给Executor运行同时SparkContext将应用程序代码发放给Executor。

- Task在Executor上运行，运行完毕释放所有资源。

### 总结![img](https://cdn.nlark.com/yuque/0/2022/png/25755760/1642066780303-15612422-a0d1-41d4-81b4-56a1d2ac2729.png)

## Spark Shuffle