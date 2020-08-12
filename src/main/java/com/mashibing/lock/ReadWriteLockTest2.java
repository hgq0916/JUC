package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.lock.ReadWriteLockTest
 * @Description: 读写锁设置公平锁和非公平锁
 * @date 2020/8/12 11:31
 */
public class ReadWriteLockTest2 {

  ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);//true为公平锁

  Lock readLock = readWriteLock.readLock();
  Lock writeLock = readWriteLock.writeLock();

  private int i = 100;

  public void read(Lock lock){
    try {
      lock.lock();
      System.out.println(Thread.currentThread().getName()+" read over,i="+i);
    }finally {
      lock.unlock();
    }

  }

  public void write(Lock lock,int value){
    try {
      lock.lock();
      i = value;
      System.out.println(Thread.currentThread().getName()+" write over,i="+i);
    }finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ReadWriteLockTest2 readWriteLockTest = new ReadWriteLockTest2();

    Thread[] readThreads = new Thread[18];
    Thread[] writeThreads = new Thread[2];
    for(int i=0;i<readThreads.length;i++){
      readThreads[i] = new Thread(()->{
        for(int j=0;j<10;j++){
          readWriteLockTest.read(readWriteLockTest.readLock);
        }

      },"read"+i);
    }
    for(int i=0;i<writeThreads.length;i++){
      writeThreads[i] = new Thread(()->{
        for(int j=0;j<10;j++) {
          readWriteLockTest.write(readWriteLockTest.writeLock, 1000);
        }
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
