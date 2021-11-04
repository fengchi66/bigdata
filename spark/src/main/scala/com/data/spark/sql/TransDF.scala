package com.data.spark.sql

import org.apache.spark.sql.SparkSession

object TransDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[1]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()


  }

}
