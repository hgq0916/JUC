package com.mashibing.reference;

import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.reference.ThreadLocalTest
 * @Description: TODO
 * @date 2020/8/14 16:25
 */
public class ThreadLocalTest {

  //ThreadLocal是线程隔离的，每个线程独有自己的变量
  static ThreadLocal<T> t1 = new ThreadLocal<>();

  public static void main(String[] args) {

    new Thread(()->{
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      t1.set(new T());
      System.out.println("t1 set");
      t1.remove();
    }).start();

    new Thread(()->{
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(t1.get());
    }).start();


  }

}
