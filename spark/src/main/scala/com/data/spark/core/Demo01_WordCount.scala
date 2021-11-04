package com.data.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Demo01_WordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Demo01_WordCount").setMaster("local[*]")
    val sc = new SparkContext(conf)

    sc.textFile("spark/src/main/resources/data/wikiOfSpark.txt")
      .flatMap(_.split(",", -1))
      .filter(!"".equals(_))
      .map((_, 1))
      .reduceByKey(_ + _)
      .sortBy(_._2, false)
      .take(10)

    sc.stop()
  }

}
