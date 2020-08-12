package com.mashibing.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.SemaphoreTest
 * @Description: 信号量，用于限制流量，公平和非公平
 * @date 2020/8/12 15:06
 */
public class SemaphoreTest2 {

  public static void main(String[] args) {
    Semaphore semaphore = new Semaphore(2,true);//表示限制流量为两个人,设置为true表示公平限流

    Thread[] threads = new Thread[5];

    for(int i=0;i<threads.length;i++){
      threads[i] = new Thread(()->{
        for(int j=0;j<3;j++){
          try {
            semaphore.acquire(1);//申请信号量
            System.out.println(Thread.currentThread().getName()+"线程开始执行...");
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }finally {
            semaphore.release(1);//释放信号量
          }
        }
      },"thread"+i);
    }

    for(Thread thread :threads) thread.start();

  }

}
