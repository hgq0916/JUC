package com.mashibing.synchronizedTest;

import java.util.concurrent.TimeUnit;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.synchronizedTest.Synchronized1Test
 * @Description: synchronized由偏向锁到自旋锁到重量级锁
 * @date 2020/8/11 9:38
 */
public class Synchronized3Test {

  public static void main(String[] args) throws InterruptedException {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Object o = new Object();
    System.out.println(ClassLayout.parseInstance(o).toPrintable());
    synchronized (o){
      System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
    new Thread(()->{
      synchronized (o){
        System.out.println(Thread.currentThread().getName()+":"+ClassLayout.parseInstance(o).toPrintable());
      }
    },"m1").start();
    TimeUnit.SECONDS.sleep(1);
    new Thread(()->{
      synchronized (o){
        System.out.println(Thread.currentThread().getName()+":"+ClassLayout.parseInstance(o).toPrintable());
      }
    },"m2").start();
    new Thread(()->{
      synchronized (o){
        System.out.println(Thread.currentThread().getName()+":"+ClassLayout.parseInstance(o).toPrintable());
      }
    },"m3").start();
  }

}
