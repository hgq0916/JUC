package com.mashibing.synchronize;

import java.util.concurrent.TimeUnit;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.synchronize.Synchronized1Test
 * @Description: synchronized由偏向锁到自旋锁
 * @date 2020/8/11 9:38
 */
public class Synchronized2Test {

  public static void main(String[] args) {
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
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
      }
    }).start();
  }

}
