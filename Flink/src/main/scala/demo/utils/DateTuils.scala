package demo.utils

import java.text.SimpleDateFormat

/**
 * @author wufc
 * @create 2020-04-19 1:26 下午
 */
object DateUtils {
   def getTime(dateTime:String) ={
    val parse = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTime)
    val timeStamp = parse.getTime
    timeStamp
  }

   def getDate(timeStamp:Long) ={
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm")

    val sd = sdf.format(timeStamp)
    sd
  }

}
