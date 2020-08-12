package com.mashibing.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.CountDownLatchTest
 * @Description: 门栓，用于等待多个线程结束，作用相当于thread.join
 * @date 2020/8/12 13:50
 */
public class CountDownLatchTest {

  public static void main(String[] args) {

    Thread[] threads = new Thread[100];
    CountDownLatch countDownLatch = new CountDownLatch(threads.length);

    for(int i=0;i<threads.length;i++){
      threads[i] = new Thread(()->{
        try{
          System.out.println(Thread.currentThread().getName()+" count:"+countDownLatch.getCount());
        }finally {
          countDownLatch.countDown();
        }
      },"thread"+i);
    }

    for(Thread thread:threads) thread.start();

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("所以的线程执行结束");

  }

}
