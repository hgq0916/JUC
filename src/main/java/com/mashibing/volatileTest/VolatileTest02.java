package com.mashibing.volatileTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.volatileTest.VolatileTest02
 * @Description: volatile可以保证变量在线程间可见
 * @date 2020/8/11 13:42
 */
public class VolatileTest02 {

  private /*volatile*/ boolean flag = true;

  public void m(){
    System.out.println("m start...");
    while (flag){
    }
    System.out.println("m end...");
  }

  public static void main(String[] args) throws InterruptedException {
    VolatileTest02 test02 = new VolatileTest02();
    new Thread(test02::m).start();
    TimeUnit.SECONDS.sleep(1);
    test02.flag = false;
  }

}


