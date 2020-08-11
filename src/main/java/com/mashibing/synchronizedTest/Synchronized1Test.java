package com.mashibing.synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.synchronizedTest.Synchronized1Test
 * @Description: synchronized由无锁到自旋锁
 * @date 2020/8/11 9:38
 */
public class Synchronized1Test {

  public static void main(String[] args) {
    Object o = new Object();
    System.out.println(ClassLayout.parseInstance(o).toPrintable());
    synchronized (o){
      System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
  }

}
