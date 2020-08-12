package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.lock.ReentrantLock
 * @Description: tryLock可以尝试获取锁
 * @date 2020/8/12 10:15
 */
public class ReentrantLockTest03 {

  Lock lock = new ReentrantLock();

  public void m1(){
    try{
      lock.lock();
      for(int i=0;i<3;i++){
        System.out.println("m1:"+i);
        TimeUnit.SECONDS.sleep(1);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public void m2(){
    boolean trylock = false;
    try{
      //trylock = lock.tryLock();//尝试获取锁，获取不到则放弃
      trylock = lock.tryLock(5,TimeUnit.SECONDS);
      System.out.println("m2 尝试获取锁："+trylock);
      for(int i=0;i<2;i++){
        System.out.println("m2"+":i="+i);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if(trylock) lock.unlock();
    }
  }

  public static void main(String[] args) {
    ReentrantLockTest03 test01 = new ReentrantLockTest03();
    new Thread(test01::m1,"m1").start();
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    new Thread(test01::m2,"m2").start();
  }


}
