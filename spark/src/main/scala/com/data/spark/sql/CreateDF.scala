package com.data.spark.sql

import org.apache.spark.sql.SparkSession

/**
 * 创建DataFrame
 */
object CreateDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[1]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._
    // 1. 从RDD中创建
    val seq = Seq(("Bob", 14), ("Alice", 18))
    val df1 = spark.sparkContext.parallelize(seq).toDF("name", "age")

    // 2. 从文件系统创建 DataFrame
    spark.read.parquet("")
    spark.read.orc("")

    // 3. 数据库读取
    spark.read.format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", "jdbc:mysql://hostname:port/mysql")
      .option("user", "用户名")
      .option("password","密码")
      .option("numPartitions", 20)
      .option("dbtable", "数据表名 ")
      .load()

    // 3. hive表

    df1.show()



    spark.stop()

  }

}
