package com.big.data.thread;

public class NewThread {

  public static void main(String[] args) {
    NewThread1 thread1 = new NewThread1();
    thread1.start();

    NewThread2 thread2 = new NewThread2();
    new Thread(thread2).start();


  }

}

class NewThread1 extends Thread {

  @Override
  public void run() {
    System.out.println("启动一个线程2。。。");
  }
}

class NewThread2 implements Runnable {

  @Override
  public void run() {
    System.out.println("启动一个线程2。。。");
  }
}
