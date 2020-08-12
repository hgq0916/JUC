package com.mashibing.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicLongTest {

  AtomicLong num1 = new AtomicLong(0);
  LongAdder longAdder = new LongAdder();
  long count = 0;

  public void atomicLong(){
    for(int i=0;i<10000;i++){
      num1.incrementAndGet();
    }
  }

  public void longAdder(){
    for(int i=0;i<100000;i++){
      longAdder.increment();
    }
  }

  public synchronized void synchronizedTest(){
    for(int i=0;i<100000;i++){
      count++;
    }
  }

  public static void main(String[] args) throws InterruptedException {
    AtomicLongTest atomicLongTest = new AtomicLongTest();

    int size = 1000;

    Thread[] atomicThreads = new Thread[size];
    for(int i=0;i<atomicThreads.length;i++){
      atomicThreads[i] = new Thread(atomicLongTest::atomicLong);
    }

    long t1 = System.currentTimeMillis();

    for(Thread thread : atomicThreads){
      thread.start();
    }
    for(Thread thread : atomicThreads){
      thread.join();
    }
    long t2 = System.currentTimeMillis();
    System.out.println("atomic:"+(t2-t1));

    Thread[] adderThreads = new Thread[size];
    for(int i=0;i<adderThreads.length;i++){
      adderThreads[i] = new Thread(atomicLongTest::longAdder);
    }

    long t3 = System.currentTimeMillis();

    for(Thread thread : adderThreads){
      thread.start();
    }
    for(Thread thread : adderThreads){
      thread.join();
    }
    long t4 = System.currentTimeMillis();
    System.out.println("adder:"+(t4-t3));

    Thread[] syncThreads = new Thread[size];
    for(int i=0;i<syncThreads.length;i++){
      syncThreads[i] = new Thread(atomicLongTest::synchronizedTest);
    }

    long t5 = System.currentTimeMillis();

    for(Thread thread : syncThreads){
      thread.start();
    }
    for(Thread thread : syncThreads){
      thread.join();
    }
    long t6 = System.currentTimeMillis();
    System.out.println("sync:"+(t6-t5));

    System.out.println("atomic:"+atomicLongTest.num1.get());
    System.out.println("adder:"+atomicLongTest.longAdder.sum());
    System.out.println("count:"+atomicLongTest.count);

  }

}
