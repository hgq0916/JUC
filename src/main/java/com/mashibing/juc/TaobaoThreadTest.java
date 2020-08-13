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

/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */
public class TaobaoThreadTest {

  public static void main(String[] args) {

    MyContainer<Integer> container = new MyContainer<>();
    Thread t1 = null,t2 = null;

    Object lock = new Object();

    t1 = new Thread(()->{
      synchronized (lock){
        for(int i=0;i<10;i++){
          if(i == 5){
            try {
              lock.notify();
              lock.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }finally {

            }
          }
          container.add(i);
          System.out.println(Thread.currentThread().getName()+" i="+i);
        }
      }
    });

    t2 = new Thread(()->{
      synchronized (lock){
        while (container.size() !=5){
          try {
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println("线程2结束,size="+container.size());
        lock.notify();
      }
    });

    t1.start();
    t2.start();

  }

}

class MyContainer<T> {

  private List<T> list = Collections.synchronizedList(new ArrayList<>());

  public void add(T value){
    list.add(value);
  }

  public int size(){
    return list.size();
  }

}