package program.bean

/**
 * @author wufc
 * @create 2020-04-01 3:06 下午
 */
case class UserBehavior(userId: Long, itemId: Long, categoryId: Int, behavior: String, timestamp: Long)
case class ItemViewCount(itemId: Long, windowEnd: Long, count: Long)
case class LoginEvent(userId: Long, ip: String, eventType: String, eventTime: Long)

case class OrderEvent(orderId: Long, eventType: String, eventTime: Long)
case class OrderResult(orderId: Long, eventType: String)

// 订单对账模块
case class OrderEventLog(orderId: String,eventType: String,txId: String,eventTime: Long)
case class ReceiptEvent(txId: String,payChannel: String,eventTime: Long)

