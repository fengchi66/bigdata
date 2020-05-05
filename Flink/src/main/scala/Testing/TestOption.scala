package Testing

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author wufc
 * @create 2020-04-26 1:43 上午
 */
object TestOption {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Test").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd1: Int = sc.makeRDD(List(1, 2, 3, 4, 5))
      .map((_, 1)).getNumPartitions

    println(rdd1)

    val rdd2 = sc.makeRDD(List(0, 1, 2, 3, 4))
      .map((_, 1)).getNumPartitions

//    val fullRDD: RDD[(Int, (Int, Option[Int]))] = rdd1.leftOuterJoin(rdd2)
//
//    fullRDD.map{
//      case (key,(value1,option)) =>
//        (key,(value1 + "|" + option.getOrElse("def")))
//    }
//      .foreach(println(_))

    sc.stop()
  }

}
