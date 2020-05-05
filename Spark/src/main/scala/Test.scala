import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author wufc
 * @create 2020-03-28 4:50 下午
 */
object Test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Test").setMaster("local[*]")
    val sc = new SparkContext(conf)

    sc.makeRDD(Array(1,2,3,4))
      .foreach(println(_))

    
    sc.stop()

  }

}
