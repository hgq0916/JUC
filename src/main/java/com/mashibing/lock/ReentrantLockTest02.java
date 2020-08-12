package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.lock.ReentrantLock
 * @Description: ReentrantLock是可重入锁
 * @date 2020/8/12 10:15
 */
public class ReentrantLockTest02 {

  Lock lock = new ReentrantLock();

  public void m1(){
    try{
      lock.lock();
      for(int i=0;i<10;i++){
        System.out.println("m1 "+":i="+i);
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if(i==2) m2();
      }
    }finally {
      lock.unlock();
    }
  }

  public void m2(){
    try{
      lock.lock();
      for(int i=0;i<2;i++){
        System.out.println("m2"+":i="+i);
      }
    }finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    ReentrantLockTest02 test01 = new ReentrantLockTest02();
    new Thread(test01::m1,"m1").start();
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //new Thread(test01::m2,"m2").start();
  }


}
