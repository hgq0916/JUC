package com.mashibing.juc;

/**
 * synchronized是可重入锁
 */
public class T01 {

  public synchronized void t1(){
    System.out.println("t1 begin");
    t2();
    System.out.println("t1 end");
  }

  private synchronized void t2() {
    System.out.println("t2 begin");
    System.out.println("t2 end");
  }

  public static void main(String[] args) {
    T01 t = new T01();

    new Thread(t::t1,"t1-thread").start();

  }

}
