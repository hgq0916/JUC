package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.lock.ReentrantLock
 * @Description: lockInterruptibly 获取锁的过程中可以被打断
 * @date 2020/8/12 10:15
 */
public class ReentrantLockTest04 {

  Lock lock = new ReentrantLock();

  public void m1(){
    try{
      lock.lock();
      System.out.println("m1获取锁");
      TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public void m2(){
    boolean trylock = false;
    try{
      System.out.println("m2 尝试获取锁...");
      //lock.lock();
      lock.lockInterruptibly();
    } catch (InterruptedException e) {
      System.out.println("m2获取锁被打断");
      e.printStackTrace();
    } finally {
      if(trylock) lock.unlock();
    }
  }

  public static void main(String[] args) {
    ReentrantLockTest04 test01 = new ReentrantLockTest04();
    new Thread(test01::m1,"m1").start();
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Thread m2 = new Thread(test01::m2, "m2");
    m2.start();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    m2.interrupt();
  }


}
