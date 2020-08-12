package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.lock.ReentrantLock
 * @Description: 两个线程竞争同一把锁
 * @date 2020/8/12 10:15
 */
public class ReentrantLockTest01 {

  Lock lock = new ReentrantLock();

  public void m1(){
    try{
      lock.lock();
      for(int i=0;i<10;i++){
        System.out.println(Thread.currentThread().getName()+":i="+i);
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }finally {
      lock.unlock();
    }
  }

  public void m2(){
    try{
      lock.lock();
      for(int i=0;i<2;i++){
        System.out.println(Thread.currentThread().getName()+":i="+i);
      }
    }finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    ReentrantLockTest01 test01 = new ReentrantLockTest01();
    new Thread(test01::m1,"m1").start();
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(test01::m2,"m2").start();
  }


}
