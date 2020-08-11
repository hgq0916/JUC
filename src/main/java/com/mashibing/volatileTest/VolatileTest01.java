package com.mashibing.volatileTest;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.volatileTest.VolatileTest01
 * @Description: volidate只能保证可见性，不能保证原子性
 * @date 2020/8/11 13:37
 */
public class VolatileTest01 {

  private volatile int count = 0;

  public synchronized void add(){
    for(int i=0;i<10000;i++){
      count++;
    }
  }

  public static void main(String[] args) {
    VolatileTest01 test01 = new VolatileTest01();
    int size = 10;
    Thread[] threads = new Thread[size];
    for(int i=0;i<threads.length;i++){
      threads[i] = new Thread(test01::add);
    }

    for(int i=0;i<threads.length;i++){
      threads[i].start();
    }

    for(int i=0;i<threads.length;i++){
      try {
        threads[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(test01.count);
  }

}
