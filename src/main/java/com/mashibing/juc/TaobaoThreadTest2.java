package com.mashibing.juc;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.TaobaoThreadTest
 * @Description: 淘宝面试题
 * @date 2020/8/13 14:11
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */
public class TaobaoThreadTest2 {

  public static void main(String[] args) {

    MyContainer2<Integer> container = new MyContainer2<>();
    Thread t1 = null,t2 = null;

    CountDownLatch latch1 = new CountDownLatch(1);
    CountDownLatch latch2 = new CountDownLatch(1);

    t1 = new Thread(()->{
        for(int i=0;i<10;i++){
          if(i == 5){
            try {
              //打开线程2，拴住线程1
              latch2.countDown();
              latch1.await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          container.add(i);
          System.out.println(Thread.currentThread().getName()+" i="+i);
        }
    });

    t2 = new Thread(()->{
        while (container.size() !=5){
          try {
            latch2.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        try{
          System.out.println("线程2结束,size="+container.size());
        }finally {
          latch1.countDown();
        }

    });

    t1.start();
    t2.start();

  }

}

class MyContainer2<T> {

  private List<T> list = Collections.synchronizedList(new ArrayList<>());

  public void add(T value){
    list.add(value);
  }

  public int size(){
    return list.size();
  }

}