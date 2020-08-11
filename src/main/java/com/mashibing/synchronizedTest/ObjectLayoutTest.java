package com.mashibing.synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.thread.synchronizedTest.ObjectLayoutTest
 * @Description: TODO
 * @date 2020/8/10 13:27
 */
public class ObjectLayoutTest {

  public static void main(String[] args) throws InterruptedException {
    //
    //Thread.sleep(5000);
    Object o = new Object();
    System.out.println(ClassLayout.parseInstance(o).toPrintable());
    synchronized (o){
      System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
  }

}
