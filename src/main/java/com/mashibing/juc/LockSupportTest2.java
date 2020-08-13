package com.mashibing.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.LockSupportTest
 * @Description: TODO
 * @date 2020/8/13 14:02
 */
public class LockSupportTest2 {

  public static void main(String[] args) {
    Thread t1;
    t1 = new Thread(()->{
      for(int i=0;i<10;i++){
        System.out.println(Thread.currentThread().getName()+" i="+i);
        if(i == 5){
          LockSupport.park();
        }
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    t1.start();
    //线程启动立即unpark,先于park执行
    LockSupport.unpark(t1);

  }

}
