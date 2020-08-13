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
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */
public class TaobaoThreadTest4 {

  public static void main(String[] args) {

    MyContainer3<Integer> container = new MyContainer3<>();

    final AtomicReference<Thread> t1 = new AtomicReference<>();
    final AtomicReference<Thread> t2 = new AtomicReference<>();

    t1.set(new Thread(()->{
      for(int i=0;i<10;i++){
        if(i == 5){
          try {
            t2.get().join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        container.add(i);
        System.out.println(Thread.currentThread().getName()+" i="+i);
      }
    }));

    t2.set(new Thread(()->{
      while (container.size() !=5){
        Thread.yield();
      }

      try{
        System.out.println("线程2结束,size="+container.size());
      }finally {
      }

    }));

    t1.get().start();
    t2.get().start();

  }

}

class MyContainer4<T> {

  private List<T> list = Collections.synchronizedList(new ArrayList<>());

  public void add(T value){
    list.add(value);
  }

  public int size(){
    return list.size();
  }

}