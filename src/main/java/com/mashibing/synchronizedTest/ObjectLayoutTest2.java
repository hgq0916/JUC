package com.mashibing.synchronizedTest;

import java.util.concurrent.TimeUnit;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.thread.synchronizedTest.ObjectLayoutTest
 * @Description: TODO
 * @date 2020/8/10 13:27
 */
public class ObjectLayoutTest2 {

  Object o = new Object();

  public  void print(){
    synchronized (o){
      /*try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }*/
      System.out.println(Thread.currentThread().getName()+":"+ClassLayout.parseInstance(o).toPrintable());
    }
  }

  public static void main(String[] args) throws InterruptedException {
    TimeUnit.SECONDS.sleep(5);
    ObjectLayoutTest2 olt = new ObjectLayoutTest2();
    System.out.println("main:"+ClassLayout.parseInstance(olt.o).toPrintable());

    new Thread(olt::print,"m1").start();
    //new Thread(olt::print,"m2").start();
    //new Thread(olt::print,"m3").start();


  }

}
