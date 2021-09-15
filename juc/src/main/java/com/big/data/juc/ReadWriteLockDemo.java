package com.big.data.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁: ReadWriteLock lock = new ReentrantReadWriteLock();
 */
public class ReadWriteLockDemo {

  public static void main(String[] args) {

    MyCache cache = new MyCache();

    // 写入
    for (int i = 0; i < 5; i++) {
      final int temp = i;
      new Thread(() -> {
        cache.put(temp, temp);
      }, i + "").start();
    }

    // 读取
    for (int i = 0; i < 5; i++) {
      final int temp = i;
      new Thread(() -> {
        cache.get(temp);
      }, i + "").start();
    }
  }

}

/**
 * 自定义缓存
 */
class MyCache {

  private volatile Map<Integer, Integer> map = new HashMap<>();

  // 读写锁，写入的时候只有一个线程写
  private ReadWriteLock lock = new ReentrantReadWriteLock();


  // 写入的时候只能同时由一个线程写
  public void put(int key, int value) {
    lock.writeLock().lock();

    try {
      System.out.println(Thread.currentThread().getName() + "写入：" + key);
      map.put(key, value);
      System.out.println(Thread.currentThread().getName() + "写入OK：" + key);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.writeLock().unlock();
    }

  }

  // 读取的时候可以同时由多个线程读
  public void get(int key) {
    lock.readLock().lock();
    try {
      System.out.println(Thread.currentThread().getName() + "读取：" + key);
      map.get(key);
      System.out.println(Thread.currentThread().getName() + "读取：" + key);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.readLock().unlock();
    }
  }

}
