package com.mashibing.juc;

import java.util.concurrent.Exchanger;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.ExchangerTest
 * @Description: 交换器，用于两个线程交换数据
 * @date 2020/8/12 15:16
 */
public class ExchangerTest {

  private Exchanger exchanger = new Exchanger();

  public void m1(){
    String str = "m1";
    try {
      String s = (String) exchanger.exchange(str);
      System.out.println("m1 method:"+s);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void m2(){
    String str = "m2";
    try {
      String s = (String) exchanger.exchange(str);
      System.out.println("m2 method:"+s);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ExchangerTest exchangerTest = new ExchangerTest();
    new Thread(exchangerTest::m1).start();
    new Thread(exchangerTest::m2).start();
  }


}

