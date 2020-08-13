package com.mashibing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

  private volatile double x,y;

  private StampedLock lock = new StampedLock();

  public void move(double detailX,double detailY){
    //写锁为独占锁
    long stamp = lock.writeLock();
    try{
      x += detailX;
      y += detailY;
      TimeUnit.SECONDS.sleep(1);
      System.out.println(Thread.currentThread().getName()+" x,y="+x+","+y);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlockWrite(stamp);
    }
  }

  public double distanceFromOrigin(){

    //尝试乐观读
      long stamp = lock.tryOptimisticRead();
      double oldX = x;
      double oldY = y;
      if(!lock.validate(stamp)){
        System.out.println("尝试获取悲观锁:"+Thread.currentThread().getName()+" oldX,oldY="+oldX+","+oldY);
        //数据失效，尝试获取读锁
        long stamp1 = lock.readLock();
        try{
          oldX = x;
          oldY = y;
          TimeUnit.SECONDS.sleep(1);
          System.out.println("获取到悲观读锁:"+Thread.currentThread().getName()+" oldX,oldY="+oldX+","+oldY);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlockRead(stamp1);
        }
      }else {
        System.out.println("获取到乐观读锁:"+Thread.currentThread().getName()+" oldX,oldY="+oldX+","+oldY);
      }
    return Math.sqrt(oldX*oldX+oldY*oldY);
  }

  public void moveIfOrigin(double newX,double newY){
    //将读锁转换为写锁
    long readStamp = lock.readLock();
    while (x == 0.0 && y==0.0){
      //尝试将读锁升级为写锁
      long writeStamp = lock.tryConvertToWriteLock(readStamp);
      System.out.println("尝试将读锁升级为写锁:"+writeStamp);
      try{
        if(writeStamp !=0){
          //写锁获取成功
          x = newX;
          y = newY;
          break;
        }else {
          //释放读锁，重新获取写锁
          lock.unlockRead(readStamp);
          writeStamp = lock.writeLock();
        }
      }finally {
        if(writeStamp != 0){
          lock.unlockWrite(writeStamp);
        }
      }

    }
  }

  public static void main(String[] args) {
    StampedLockTest stampedLockTest = new StampedLockTest();

    Thread[] readThreads = new Thread[10];
    Thread[] writeThreads = new Thread[2];

    for(int i=0;i<readThreads.length;i++){
      readThreads[i] = new Thread(()->{
        double distance = stampedLockTest.distanceFromOrigin();
        System.out.println(Thread.currentThread().getName()+" distance:"+distance);
      },"read"+i);
    }

    for(int i=0;i<writeThreads.length;i++){
      writeThreads[i] = new Thread(()->{
        double x = 1.0;
        double y = 2.0;
       stampedLockTest.move(x,y);
      },"write"+i);
    }

    for(Thread thread:readThreads) thread.start();
    for(Thread thread:writeThreads) thread.start();

    //尝试锁升级
    stampedLockTest.moveIfOrigin(2,3);

  }

}
