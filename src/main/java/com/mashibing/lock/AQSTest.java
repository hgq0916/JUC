package com.mashibing.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSTest {

  private Lock lock = new ReentrantLock();

  public void testLock(){

    try{
      lock.lock();
      System.out.println(Thread.currentThread().getName()+"获取锁成功");
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    AQSTest aqsTest = new AQSTest();
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for(int i=0;i<10;i++){
      executorService.execute(()->{
        aqsTest.testLock();
      });
    }
    aqsTest.testLock();
  }

}
