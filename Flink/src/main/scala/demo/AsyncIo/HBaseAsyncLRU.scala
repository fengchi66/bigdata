package demo.AsyncIo

import java.util
import java.util.Collections
import java.util.concurrent.TimeUnit

import demo.bean.OrderEvent
import org.apache.flink.configuration.Configuration
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import org.apache.flink.streaming.api.scala.async.{ResultFuture, RichAsyncFunction}
import org.hbase.async.{GetRequest, HBaseClient, KeyValue}

/**
 * @author wufc
 * @create 2020-05-04 8:53 下午
 */
class HBaseAsyncLRU(zk: String, tableName: String, maxSize: Long, ttl: Long)
  extends RichAsyncFunction[OrderEvent, (String, String)] {

  private var hbaseClient: HBaseClient = _
  private var cache: Cache[String, String] = _


  override def open(parameters: Configuration): Unit = {

    hbaseClient = new HBaseClient(zk)

    cache = CacheBuilder.newBuilder()
      .maximumSize(maxSize)
      .expireAfterWrite(ttl, TimeUnit.SECONDS)
      .build()

  }

  override def asyncInvoke(input: OrderEvent, resultFuture: ResultFuture[(String, String)]): Unit = {
    val key: String = input.userId.toString
    val value: String = cache.getIfPresent(key) //从缓存中获取值

    if (value != null) { //说明命中
      import scala.collection.JavaConversions._
      resultFuture.complete(Collections.singleton((key, value)))
      return
    }

    //    在缓冲中没有查到，则在HBase中查，并将结果放入缓冲中
    val get = new GetRequest(tableName, key)



  }
}
