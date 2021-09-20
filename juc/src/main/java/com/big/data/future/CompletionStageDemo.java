package com.big.data.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletionStageDemo {

  public static void main(String[] args) {
//    CompletableFuture<String> f0 = CompletableFuture
//        .supplyAsync(() -> "hello world")
//        .thenApply(s -> s + " QQ")
//        .thenApply(s -> s.toUpperCase());
//
//    System.out.println("main...");
//    System.out.println(f0.join());

    CompletableFuture<String> f1 =
        CompletableFuture.supplyAsync(() -> {
          int t = getRandom();
          sleep(t, TimeUnit.SECONDS);
          return String.valueOf(t);
        });

    CompletableFuture<String> f2 =
        CompletableFuture.supplyAsync(() -> {
          int t = getRandom();
          sleep(t, TimeUnit.SECONDS);
          return String.valueOf(t);
        });

    CompletableFuture<String> f3 =
        f1.applyToEither(f2, s -> s);

    System.out.println(f3.join());
  }

  static void sleep(int t, TimeUnit u) {
    try {
      u.sleep(t);
    } catch (InterruptedException e) {
    }
  }

  static int getRandom() {
    return (int) Math.random();
  }

}
