package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.lock.ReentrantLock
 * @Description: lockInterruptibly 公平锁和非公平锁
 * @date 2020/8/12 10:15
 */
public class ReentrantLockTest05 {

  Lock lock = new ReentrantLock(false);//true表示公平锁

  public void m1(){

      for(int i=0;i<100;i++){
        try{
          lock.lock();
          System.out.println("m1获取锁："+i);
        }finally {
          lock.unlock();
        }
      }
  }

  public void m2(){
      for(int i=0;i<100;i++){
        try{
        lock.lock();
        System.out.println("m2获取锁："+i);
        }finally {
          lock.unlock();
        }
      }
  }

  public static void main(String[] args) {
    ReentrantLockTest05 test01 = new ReentrantLockTest05();
    new Thread(test01::m1,"m1").start();
    new Thread(test01::m2, "m2").start();
  }


}
