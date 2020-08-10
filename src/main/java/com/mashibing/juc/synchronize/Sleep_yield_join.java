package com.mashibing.juc.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.synchronize.Sleep_yeild_join
 * @Description: TODO
 * @date 2020/8/10 18:03
 */
public class Sleep_yield_join {


  public void t1(){
    System.out.println("t1 begin");
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("t1 end");
  }

  public void t2(){
    for(int i=0;i<10;i++){
      if(i==5){
        Thread.yield();
      }
      System.out.println(Thread.currentThread().getName()+":i="+i);
    }
  }

  public static void main(String[] args) {
    Sleep_yield_join obj = new Sleep_yield_join();
    //sleep
   /* new Thread(()->{
      obj.t1();
    }).start();*/

   //yield
   /* new Thread(()->{
      obj.t2();
    }).start();

    for(int i=0;i<10;i++){
      System.out.println(Thread.currentThread().getName()+":i="+i);
    }*/
   //join
    Thread tt = new Thread(()->{
      obj.t1();
    });
    tt.start();

    try {
      tt.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("main end");


  }


}
