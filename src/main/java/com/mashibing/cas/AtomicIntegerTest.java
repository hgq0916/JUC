package com.mashibing.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.cas.AtomicIntegerTest
 * @Description: TODO
 * @date 2020/8/11 14:18
 */
public class AtomicIntegerTest {

  private AtomicInteger count = new AtomicInteger(0);

  public void inc(){
    for(int i=0;i<10000;i++){
      count.incrementAndGet();
    }
  }

  public static void main(String[] args) {
    AtomicIntegerTest atomicIntegerTest = new AtomicIntegerTest();

    Thread[] threads = new Thread[10];
    for(int i=0;i<threads.length;i++){
      threads[i] = new Thread(atomicIntegerTest::inc);
    }
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
    System.out.println(atomicIntegerTest.count.get());
  }

}
