package com.mashibing.juc;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.SynchronizedContainerTest
 * @Description: 面试题
 * @date 2020/8/13 15:37
 */

import java.util.LinkedList;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生成者线程以及10个消费者线程的阻塞调用
 */
public class SynchronizedContainerTest {

  public static void main(String[] args) throws InterruptedException {

    MySynchronizedContainer<String> container = new MySynchronizedContainer();

    Thread[] producers = new Thread[2];
    Thread[] consumers = new Thread[10];

    for(int i=0;i<producers.length;i++){
      producers[i] = new Thread(()->{
        for(int j=0;j<50;j++){
          container.put(Thread.currentThread().getName()+"_"+j);
        }
      },"p"+i);
    }

    for(int i=0;i<consumers.length;i++){
      consumers[i] = new Thread(()->{
        for(int j=0;j<10;j++){
          String str = container.get();
        }
      },"c"+i);
    }

    for(Thread thread:producers) thread.start();
    for(Thread thread:consumers) thread.start();

    for(Thread thread:producers) thread.join();
    for(Thread thread:consumers) thread.join();

    System.out.println("剩余产品个数："+container.getCount());

  }
}

class MySynchronizedContainer<T> {

  private LinkedList<T> list = new LinkedList<>();

  int MAX = 10;

  public synchronized void put(T value){
    while (list.size() == MAX){
      this.notifyAll();
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    list.push(value);
    System.out.println(Thread.currentThread().getName()+"生产了"+value);
    //唤醒消费者
    this.notifyAll();
  }

  public synchronized T get(){
    while (list.size() == 0){
      this.notifyAll();
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    T value = list.pop();
    System.out.println(Thread.currentThread().getName()+" 消费 "+value);
    this.notifyAll();
    return value;
  }

  public synchronized int getCount(){
    return list.size();
  }

}
