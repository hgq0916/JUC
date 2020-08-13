package com.mashibing.juc;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.SynchronizedPrintTest
 * @Description: TODO
 * @date 2020/8/13 16:25
 */

/**
 * 两个线程交替输出A1 ,B2,C3 ...Z26
 */
public class SynchronizedPrintTest4 {

  private volatile boolean flag = false;

  public static void main(String[] args) {

    SynchronizedPrintTest4 t = new SynchronizedPrintTest4();

    new Thread(()->{
      for(char c ='A';c<='Z';c++){
        synchronized (t){
            System.out.println(c);
            if(c == 'A'){
              t.flag = true;
            }
            t.notify();
            if(c != 'Z'){
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
      while (!t.flag){
        try {
          t.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      for(int i=1;i<=26;i++){
        synchronized (t){
          System.out.println(i);
          t.notify();
          if(i != 26){
            try {
              t.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }).start();

  }

}
