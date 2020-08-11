package com.mashibing.volatileTest;

import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.volatileTest.DCLTest
 * @Description: TODO
 * @date 2020/8/11 13:52
 */
public class DCLTest {

  private int i = 100;

  private volatile static DCLTest INSTANCE;

  private DCLTest(){
    try {
      TimeUnit.MICROSECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static DCLTest getInstance(){
    if(INSTANCE == null){
      synchronized (DCLTest.class){
        if(INSTANCE == null){
          INSTANCE = new DCLTest();
        }
      }
    }
    return INSTANCE;
  }

  public static void main(String[] args) {
    Thread[] threads = new Thread[1000000];
    for(int i=0;i<threads.length;i++){
      final int j = i;
      threads[i] = new Thread(()->{
        int a = -1;
        if((a=DCLTest.getInstance().i)!=100){
          System.out.println("thread:"+Thread.currentThread().getName()+"a:"+a);
        }

      },"thread"+i);
    }
    for(int i=0;i<threads.length;i++){
      threads[i].start();
    }
  }

}
