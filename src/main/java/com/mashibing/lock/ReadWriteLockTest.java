package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.lock.ReadWriteLockTest
 * @Description: 读写锁，读锁为共享锁，写锁为排它锁
 * @date 2020/8/12 11:31
 */
public class ReadWriteLockTest {

  ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  Lock lock1 = new ReentrantLock();

  Lock readLock = readWriteLock.readLock();
  Lock writeLock = readWriteLock.writeLock();

  private int i = 100;

  public void read(Lock lock){
    try {
      lock.lock();
      TimeUnit.SECONDS.sleep(1);
      System.out.println(Thread.currentThread().getName()+" read over,i="+i);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally {
      lock.unlock();
    }

  }

  public void write(Lock lock,int value){
    try {
      lock.lock();
      TimeUnit.SECONDS.sleep(1);
      i = value;
      System.out.println(Thread.currentThread().getName()+" write over,i="+i);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();

    Thread[] readThreads = new Thread[18];
    Thread[] writeThreads = new Thread[2];
    for(int i=0;i<readThreads.length;i++){
      readThreads[i] = new Thread(()->{
          //readWriteLockTest.read(readWriteLockTest.readLock);
          readWriteLockTest.read(readWriteLockTest.lock1);
      },"read"+i);
    }
    for(int i=0;i<writeThreads.length;i++){
      writeThreads[i] = new Thread(()->{
          //readWriteLockTest.write(readWriteLockTest.writeLock, 1000);
          readWriteLockTest.write(readWriteLockTest.lock1,1000);
      },"write"+i);
    }

    long t1 = System.currentTimeMillis();
    for(Thread thread:readThreads) thread.start();
    for(Thread thread:writeThreads) thread.start();

    for(Thread thread:readThreads) thread.join();
    for(Thread thread:writeThreads) thread.join();

    long t2 = System.currentTimeMillis();

    System.out.println("耗时："+(t2-t1));
  }

}
