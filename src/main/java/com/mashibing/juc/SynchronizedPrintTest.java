package com.mashibing.juc;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.SynchronizedPrintTest
 * @Description: TODO
 * @date 2020/8/13 16:25
 */

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 两个线程交替输出A1 ,B2,C3 ...Z26
 */
public class SynchronizedPrintTest {

  public static void main(String[] args) {

    Thread t1,t2;

    Object lock = new Object();

    t2 = new Thread(()->{
      for(int i=1;i<=26;i++){
        synchronized (lock) {
          try {
            System.out.println(i);
            lock.notify();
            if(i !=26){
              lock.wait();
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });

    t1 = new Thread(()->{
      for(char c ='A';c<='Z';c++){
        synchronized (lock){
          System.out.println(c);
          try {
            if(c == 'A'){
              t2.start();
            }
            lock.notify();
            if(c !='Z'){
              lock.wait();
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });

    t1.start();

  }

}
