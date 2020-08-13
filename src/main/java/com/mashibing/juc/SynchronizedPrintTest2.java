package com.mashibing.juc;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.SynchronizedPrintTest
 * @Description: TODO
 * @date 2020/8/13 16:25
 */

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 两个线程交替输出A1 ,B2,C3 ...Z26
 */
public class SynchronizedPrintTest2 {

  public static void main(String[] args) {

    AtomicReference<Thread> t1 = new AtomicReference<>();
    AtomicReference<Thread> t2 = new AtomicReference<>();

    t1.set(new Thread(()->{
      for(char c ='A';c<='Z';c++){
        System.out.println(c);
        LockSupport.unpark(t2.get());
        if(c != 'Z'){
          LockSupport.park();
        }
      }
    }));

    t2.set(new Thread(()->{
      LockSupport.park();
      for(int i=1;i<=26;i++){
        System.out.println(i);
        LockSupport.unpark(t1.get());
        if(i != 26){
          LockSupport.park();
        }
      }
    }));

    t1.get().start();
    t2.get().start();

  }

}
