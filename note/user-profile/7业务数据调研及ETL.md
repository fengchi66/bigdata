## 数据架构

在用户画像简介的文章中，介绍了一个企业通用的用户画像的数据架构，也就是一个典型的Lambda架构，将数据存储、计算分别分为离线层与在线层(实时层)。这里参考用户画像工程化一书：

在整个工程化方案中，系统依赖的基础设施包括**Spark、Hive、HBase、Airflow、MySQL、Redis、Elasticsearch**。除去基础设施外，系统主体还包括Flink、ETL、产品端** 3个重要组成部分。图 2-1 所示是用户画像数仓架构图，下面对其进行详细介绍。

![68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303231303131393030353731303936352e706e673f2c747970655f5a6d46755a33706f5a57356e6147567064476b2c736861646f775f31302c746578745f6148523063484d364c7939696247396e4c6d4e7a5a473475626d56304c](../../../../Desktop/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303231303131393030353731303936352e706e673f2c747970655f5a6d46755a33706f5a57356e6147567064476b2c736861646f775f31302c746578745f6148523063484d364c7939696247396e4c6d4e7a5a473475626d56304c.png)

上面虚线框中为常见的**数据仓库ETL加工流程**，也就是将**每日的业务数据、日志数据、埋点数据等经过ETL过程，加工到数据仓库对应的ODS层、DW层、DM层中**。

中间的虚线框即为用户画像建模的主要环节，**用户画像不是产生数据的源头，而是对基于数据仓库ODS层、DW层、DM层中与用户相关数据的二次建模加工**。在ETL过程中将用户标签计算结果写入**Hive**，由于不同数据库有不同的应用场景，后续需要进一步将数据同步到`MySQL`、`HBase`、`Elasticsearch` 等数据库中。

- Hive：**存储用户标签计算结果**、**用户人群计算结果**、**用户特征库计算结果**
- MySQL：**存储标签元数据**，**监控相关数据**，**导出到业务系统的数据**
- HBase：**存储线上接口实时调用类数据**
- Elasticserch：**支持海量数据的实时查询分析**，**用于存储用户人群计算、用户群透视分析所需的用户标签数据**（由于用户人群计算、用户群透视分析的条件转化成的SQL语句多条件嵌套较为复杂，使用 Impala 执行也需花费大量时间）

**用户标签数据在Hive中加工完成后，部分标签通过Sqoop同步到MySQL数据库，提供用于BI报表展示的数据、多维透视分析数据、圈人服务数据；另一部分标签同步到HBase数据库用于产品的线上个性化推荐**

## 业务数据ETL

### Mysql数据库

在数仓系统中，庞大业务数据的数据都是存在mysql数据库中，然后使用Sqoop/Datax等工具同步到Hive中。在这里不关注与数仓的建模实现等，只关心标签数据的开发。在实时数仓项目中，使用数据模拟脚本模拟了订单系统的一些业务表。

在`gmall2021`数据库中存在47张业务表，只同步`order_info`表中的部分字段。

### 用户订单表结构

```sql
mysql> desc order_info;
+------------------------+---------------+------+-----+---------+----------------+
| Field                  | Type          | Null | Key | Default | Extra          |
+------------------------+---------------+------+-----+---------+----------------+
| id                     | bigint        | NO   | PRI | NULL    | auto_increment |
| consignee              | varchar(100)  | YES  |     | NULL    |                |
| consignee_tel          | varchar(20)   | YES  |     | NULL    |                |
| total_amount           | decimal(10,2) | YES  |     | NULL    |                |
| order_status           | varchar(20)   | YES  |     | NULL    |                |
| user_id                | bigint        | YES  |     | NULL    |                |
| payment_way            | varchar(20)   | YES  |     | NULL    |                |
| delivery_address       | varchar(1000) | YES  |     | NULL    |                |
| order_comment          | varchar(200)  | YES  |     | NULL    |                |
| out_trade_no           | varchar(50)   | YES  |     | NULL    |                |
| trade_body             | varchar(200)  | YES  |     | NULL    |                |
| create_time            | datetime      | YES  |     | NULL    |                |
| operate_time           | datetime      | YES  |     | NULL    |                |
| expire_time            | datetime      | YES  |     | NULL    |                |
| process_status         | varchar(20)   | YES  |     | NULL    |                |
| tracking_no            | varchar(100)  | YES  |     | NULL    |                |
| parent_order_id        | bigint        | YES  |     | NULL    |                |
| img_url                | varchar(200)  | YES  |     | NULL    |                |
| province_id            | int           | YES  |     | NULL    |                |
| activity_reduce_amount | decimal(16,2) | YES  |     | NULL    |                |
| coupon_reduce_amount   | decimal(16,2) | YES  |     | NULL    |                |
| original_total_amount  | decimal(16,2) | YES  |     | NULL    |                |
| feight_fee             | decimal(16,2) | YES  |     | NULL    |                |
| feight_fee_reduce      | decimal(16,2) | YES  |     | NULL    |                |
| refundable_time        | datetime      | YES  |     | NULL    |                |
+------------------------+---------------+------+-----+---------+----------------+
25 rows in set (0.02 sec)
```

可以看到`order_info`表的字段信息，为实现demo，我们只同步部分字段到数仓中。

### Maxcompute数据仓库

在离线数仓的选型上，绝大部分公司都是选择Hadoop体系中的Hive，我比较懒，懒得搭建Hdoop、Hive等服务，所以直接白嫖阿里云提供的大数据服务吧，关于Maxcompute的介绍可以参考阿里云官网，Maxcompute可以看做是Hive的阿里云商业版实现。

- 开通阿里云Maxcompute服务，并创建project(相当于数据库)

- 创建ODPS对应的order_info表

  ```sql
  CREATE TABLE IF NOT EXISTS ods_order_info
  (
      id                     BIGINT  ,
      total_amount           DOUBLE  ,
      order_status           STRING  ,
      user_id                BIGINT  ,
      payment_way            STRING  ,
      delivery_address       STRING  ,
      create_time            DATETIME,
      operate_time           DATETIME,
      province_id            BIGINT  ,
      activity_reduce_amount DOUBLE  ,
      coupon_reduce_amount   DOUBLE  ,
      original_total_amount  DOUBLE  
  ) ;
  ```

### Datax同步

[datax文档](https://github.com/alibaba/DataX)

- Datax同步mysql表order_info数据到ODPS

  ```json
  {
      "job":{
          "setting":{
              "speed":{
                  "channel":3
              },
              "errorLimit":{
                  "record":0,
                  "percentage":0.02
              }
          },
          "content":[
              {
                  "reader":{
                      "name":"mysqlreader",
                      "parameter":{
                          "username":"root",
                          "password":"992318abC",
                          "column":[
                              "id",
                              "total_amount",
                              "order_status",
                              "user_id",
                              "payment_way",
                              "delivery_address",
                              "create_time",
                              "operate_time",
                              "province_id",
                              "activity_reduce_amount",
                              "coupon_reduce_amount",
                              "original_total_amount"
                          ],
                          "splitPk":"id",
                          "connection":[
                              {
                                  "table":[
                                      "order_info"
                                  ],
                                  "jdbcUrl":[
                                      "jdbc:mysql://localhost:3306/gmall2021"
                                  ]
                              }
                          ]
                      }
                  },
                  "writer":{
                      "name":"odpswriter",
                      "parameter":{
                          "project":"hadoop101",
                          "table":"ods_order_info",
                          "column":[
                              "id",
                              "total_amount",
                              "order_status",
                              "user_id",
                              "payment_way",
                              "delivery_address",
                              "create_time",
                              "operate_time",
                              "province_id",
                              "activity_reduce_amount",
                              "coupon_reduce_amount",
                              "original_total_amount"
                          ],
                          "accessId":"LTAI5tALVEA6zmWQdwMv48K8",
                          "accessKey":"TUJthenXxP3k7KFKZmhClEoYn9KLtj",
                          "truncate":true,
                          "odpsServer":"http://service.odps.aliyun.com/api",
                          "tunnelServer":"http://dt.odps.aliyun.com",
                          "accountType":"aliyun"
                      }
                  }
              }
          ]
      }
  }
  ```

  