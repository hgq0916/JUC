package com.mashibing.synchronizedTest;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.synchronizedTest.SynchronizedRefineTest
 * @Description: 锁细化，对不需要加锁的代码允许线程自由访问，可提高效率
 * @date 2020/8/11 14:03
 */
public class SynchronizedRefineTest {

  private int count = 0;

  public synchronized void m1(){
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    count++;

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public void m2(){
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    synchronized (this){
      count++;
    }

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {
    SynchronizedRefineTest test = new SynchronizedRefineTest();
    Thread[] threads = new Thread[10];
    for(int i=0;i<threads.length;i++){
      //threads[i] = new Thread(test::m1);
      threads[i] = new Thread(test::m2);
    }

    long time1 = System.currentTimeMillis();

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

    long time2 = System.currentTimeMillis();
    System.out.println("耗时："+(time2-time1));
    System.out.println(test.count);

  }

}
