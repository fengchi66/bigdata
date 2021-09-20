package com.big.data.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author wufengchi
 */
public class FutureTaskDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    FutureTask futureTask = new FutureTask(() -> {
      System.out.println("-----come in FutureTask");
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "" + ThreadLocalRandom.current().nextInt(100);
    });

    new Thread(futureTask, "t1").start();

    futureTask.get();

    System.out.println("main...");

  }

}
