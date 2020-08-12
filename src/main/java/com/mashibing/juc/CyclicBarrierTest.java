package com.mashibing.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.CyclicBarrierTest
 * @Description: 循环栅栏，类似于汽车的满人发车
 * @date 2020/8/12 14:03
 */
public class CyclicBarrierTest {

  public static void main(String[] args) {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(20);

    Thread[] threads = new Thread[100];

    for(int i=0;i<threads.length;i++){
      new Thread(()->{
        try {
          cyclicBarrier.await();//没满则线程阻塞
          System.out.println(Thread.currentThread().getName()+"发车...");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
      },"thread"+i).start();

      if(i%19 ==0){
        try {
          System.out.println("sleep"+i);
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

  }

}
