package com.big.data.juc;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 写：如果队列满了，就必须阻塞等待 读：如果队列是空的，必须阻塞等待生产
 */
public class BlockingQueueDemo {

  public static void main(String[] args) {
    // 阻塞队列
    ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);

    // 添加元素
    blockingQueue.offer(1);
    blockingQueue.offer(2);
    blockingQueue.offer(3);
    blockingQueue.offer(4);

    // 读取元素
    blockingQueue.peek();
    blockingQueue.poll();

    System.out.println(blockingQueue);

  }

}
