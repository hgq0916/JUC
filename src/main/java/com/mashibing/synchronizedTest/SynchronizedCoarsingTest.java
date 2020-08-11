package com.mashibing.synchronizedTest;

import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.synchronizedTest.SynchronizedCoarsingTest
 * @Description: 锁粗化,频繁加锁和解锁会降低效率，不如将锁的粒度加粗
 * @date 2020/8/11 14:10
 */
public class SynchronizedCoarsingTest {

  private int a = 0;
  private int b = 0;

  public void m1(){
    synchronized (this){
      a++;
    }

    synchronized (this){
      b++;
    }
  }

  public synchronized void m2(){
    a++;
    b++;
  }

  public static void main(String[] args) {
    SynchronizedCoarsingTest test = new SynchronizedCoarsingTest();
    Thread[] threads = new Thread[10000];
    for(int i=0;i<threads.length;i++){
      threads[i] = new Thread(test::m1);
      //threads[i] = new Thread(test::m2);
    }

    long time1 = System.currentTimeMillis();

    for(int i=0;i<threads.length;i++){
      threads[i].start();
    }

    for(int i=0;i<threads.length;i++){
      try {
        threads[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    long time2 = System.currentTimeMillis();
    System.out.println("耗时："+(time2-time1));
    System.out.println(test.a+","+test.b);

  }

}
