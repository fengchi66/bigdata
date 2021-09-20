package com.big.data.code;

import java.util.concurrent.TimeUnit;

public class MainThreadDemo {

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      System.out.println(
          Thread.currentThread().getName() + "\t 开始运行，" + (Thread.currentThread().isDaemon()
              ? "守护线程" : "用户线程"));
      while (true) {

      }
    }, "t1");
//线程的daemon属性为true表示是守护线程，false表示是用户线程
    t1.setDaemon(true);
    t1.start();
//3秒钟后主线程再运行
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("----------main线程运行完毕");


  }

}
