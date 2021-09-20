package com.big.data.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * 电商比价需求
 */
public class CFNetMallDemo {

  public static void main(String[] args) {
    List<NetMall> list = Arrays.asList(
        new NetMall("jd"),
        new NetMall("pdd"),
        new NetMall("tmall")
    );

    System.out.println(getPriceByStep(list, "hadoop"));
    System.out.println(getPriceByAsync(list, "hadoop"));

  }

  // 迭代的方式
  public static List<String> getPriceByStep(List<NetMall> list, String spuName) {
    return list.stream()
        .map(item -> String
            .format(spuName + " %s and %.2f", item.getMallName(), item.getPrice(spuName)))
        .collect(Collectors.toList());
  }

  // 异步
  public static List<String> getPriceByAsync(List<NetMall> list, String spuName) {
    return list.stream()
        // 相当于有list.size()个异步任务执行，返回结果
        .map(item -> CompletableFuture.supplyAsync(() -> String
            .format(spuName + " %s and %.2f", item.getMallName(), item.getPrice(spuName))))
        .collect(Collectors.toList())
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }
}

@Data
class NetMall {

  private String mallName;

  public NetMall(String mallName) {
    this.mallName = mallName;
  }

  // 随机返回一个商品的价格
  public double getPrice(String spuName) {
    try {
      // 检索需要1秒钟
      TimeUnit.SECONDS.sleep(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ThreadLocalRandom.current().nextDouble() * 2 + spuName.charAt(0);
  }
}
