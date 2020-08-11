package com.mashibing.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.cas.ABATest
 * @Description: TODO
 * @date 2020/8/11 14:31
 */
public class ABATest {

  AtomicInteger count1 = new AtomicInteger(100);
  AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100,0);

  public void atomicABA(){
    count1.compareAndSet(100,101);
    count1.compareAndSet(101,100);
  }

  public void atomicInc(){
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    boolean isUpdated = count1.compareAndSet(100, 101);
    System.out.println("是否更新："+isUpdated);
  }

  public void stampABA(){
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    stampedReference.compareAndSet(100,101,stampedReference.getStamp(),stampedReference.getStamp()+1);
    stampedReference.compareAndSet(101,100,stampedReference.getStamp(),stampedReference.getStamp()+1);
  }

  public void stampInc(){
    int stamp = stampedReference.getStamp();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    boolean isUpdated = stampedReference.compareAndSet(100, 101,stamp,stamp+1);
    System.out.println("是否更新："+isUpdated);
  }

  public static void main(String[] args) throws InterruptedException {
    ABATest abaTest = new ABATest();

    Thread thread = new Thread(abaTest::atomicABA);
    Thread thread1 = new Thread(abaTest::atomicInc);

    thread.start();
    thread1.start();
    thread.join();
    thread1.join();

    //加了版本号
    Thread thread3 = new Thread(abaTest::stampABA);
    Thread thread4 = new Thread(abaTest::stampInc);

    thread3.start();
    thread4.start();
    thread3.join();
    thread4.join();
  }

}
