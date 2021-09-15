package com.big.data.juc;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

  public static void main(String[] args) {

    // 创建线程池
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        200,
        500,
        3,
        TimeUnit.SECONDS,
        new LinkedBlockingDeque<>(300),
        Executors.defaultThreadFactory(),
        new DiscardOldestPolicy()
    );

//    ExecutorService threadPool = Executors.newCachedThreadPool();

    try {
      threadPoolExecutor.execute(() -> {
        for (int i = 0; i < 100; i++) {
          System.out.println(Thread.currentThread().getName());
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      threadPoolExecutor.shutdown();
    }

  }

}
