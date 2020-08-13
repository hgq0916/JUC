package com.mashibing.juc;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.SynchronizedPrintTest
 * @Description: TODO
 * @date 2020/8/13 16:25
 */

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 两个线程交替输出A1 ,B2,C3 ...Z26
 */
public class SynchronizedPrintTest3 {

  private volatile boolean flag = true;

  public static void main(String[] args) {

    SynchronizedPrintTest3 t = new SynchronizedPrintTest3();

    new Thread(()->{
      for(char c ='A';c<='Z';){
        synchronized (t){
          if(t.flag){
            System.out.println(c);
            c++;
            t.flag = !t.flag;
            t.notify();
          }else {
              try {
                t.wait();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
          }
        }
      }
    }).start();

    new Thread(()->{
      for(int i=1;i<=26;){
        synchronized (t){
          if(t.flag){
              try {
                t.wait();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
          }else {
            System.out.println(i);
            i++;
            t.flag = !t.flag;
            t.notify();
          }
        }
      }
    }).start();

  }

}
