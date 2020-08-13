package com.mashibing.juc;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.SynchronizedContainerTest
 * @Description: 面试题
 * @date 2020/8/13 15:37
 */

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生成者线程以及10个消费者线程的阻塞调用
 * 使用lock，消费者只去唤醒生产者，而生产者只去唤醒消费者
 */
public class SynchronizedContainerTest2 {

  public static void main(String[] args) throws InterruptedException {

    MySynchronizedContainer2<String> container = new MySynchronizedContainer2();

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

class MySynchronizedContainer2<T> {

  private LinkedList<T> list = new LinkedList<>();

  Lock lock = new ReentrantLock(true);
  Condition producer = lock.newCondition();
  Condition consumer = lock.newCondition();

  int MAX = 10;

  public void put(T value){
    lock.lock();
    try{
      while (list.size() == MAX){
        //唤醒所有的消费者
        consumer.signalAll();
        try {
          //把当前线程放入producer的等待队列
          producer.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      list.push(value);
      System.out.println(Thread.currentThread().getName()+"生产了"+value);
      //唤醒消费者
      consumer.signalAll();
    }finally {
      lock.unlock();
    }

  }

  public T get(){
    lock.lock();
    try{
      while (list.size() == 0){
        //唤醒所有的生产者
        producer.signalAll();
        try {
          //当前线程进入消费者等待队列
          consumer.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      T value = list.pop();
      System.out.println(Thread.currentThread().getName()+" 消费 "+value);
      //通知生产者继续生产
      producer.signalAll();
      return value;
    }finally {
      lock.unlock();
    }

  }

  public int getCount(){
    lock.lock();
    try{
      return list.size();
    }finally {
      lock.unlock();
    }

  }

}
