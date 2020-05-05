package demo.bean


/**
 * @author wufc
 * @create 2020-04-03 3:37 下午
 */
case class SensorReading(id:String,timeStamp:Long,temperature:Double)

case class OrderLogBean(userId:Long,itemType:String,amount:Int,timeStamp:Long)

// 两个订单流，测试双流Join
case class OrderLogEvent1(orderId:Long,amount:Double,timeStamp:Long)
case class OrderLogEvent2(orderId:Long,itemId:Long,timeStamp:Long)
case class OrderResultEvent(orderId:Long,amount:Double,itemId:Long)

// 测试异步IO
case class OrderEvent(orderId:Long,userId:Long,timeStamp:Long)
case class OrderFullEvent(orderId:Long,userId:Long,name:String,age:Int,timeStamp:Long)

