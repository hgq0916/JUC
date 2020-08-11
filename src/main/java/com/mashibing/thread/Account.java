package com.mashibing.thread;

import java.util.concurrent.TimeUnit;

/**
 * synchronized既能保证原子性，又能保证可见性
 */
public class Account {

  private String name;
  private double amount;

  public synchronized void set(String name,double amount){
    this.name = name;
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.amount = amount;
  }

  public /*synchronized*/ void get(){
    System.out.println("name:"+name+",amount:"+amount);
  }

  public static void main(String[] args) {
    Account account = new Account();
    new Thread(()->{
      account.set("张三",100);
    }).start();
    new Thread(()->{
      for(int i=0;i<5;i++){
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        account.get();
      }

    }).start();
  }

}
