package com.mashibing.juc;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.PhaserTest
 * @Description: 按照阶段执行操作
 * @date 2020/8/12 14:19
 */
public class PhaserTest {

  private static Phaser marryPhaser = new MarryPhaser();


  static class Person {

    String name;

    public void millSleep(long time){
      try {
        TimeUnit.MICROSECONDS.sleep(time);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    public Person(String s) {
      this.name = s;
    }

    public void arrive(){
      long time = new Random().nextInt(1000);
      millSleep(time);
      System.out.println(this.name+"到达结婚现场");
      marryPhaser.arriveAndAwaitAdvance();
    }

    public void eat(){
      long time = new Random().nextInt(1000);
      millSleep(time);
      System.out.println(this.name+"开始吃饭");
      marryPhaser.arriveAndAwaitAdvance();

    }

    public void leave(){
      long time = new Random().nextInt(1000);
      millSleep(time);
      System.out.println(this.name+"离开结婚现场");
      marryPhaser.arriveAndAwaitAdvance();

    }

    public void hug(){
      long time = new Random().nextInt(1000);
      millSleep(time);
      if(name.equals("新郎") || name.equals("新娘")){
        System.out.println(this.name+" 进入洞房");
        marryPhaser.arriveAndAwaitAdvance();
      }else {
        marryPhaser.arriveAndDeregister();
      }
    }

  }

  public static void main(String[] args) {

    Thread[] threads = new Thread[7];

    marryPhaser.bulkRegister(threads.length);

    Person[] persons = new Person[5];
    for(int i=0;i<persons.length;i++){
      persons[i] = new Person("p"+i);
    }

    Person bridegroom = new Person("新郎");
    Person bride = new Person("新娘");

    for(int i=0;i<persons.length;i++){
      int j = i;
      threads[i] = new Thread(()->{
        persons[j].arrive();
        persons[j].eat();
        persons[j].leave();
        persons[j].hug();
      },persons[j].name);
    }

    threads[5] = new Thread(()->{
      bridegroom.arrive();
      bridegroom.eat();
      bridegroom.leave();
      bridegroom.hug();
    },bridegroom.name);

    threads[6] = new Thread(()->{
      bride.arrive();
      bride.eat();
      bride.leave();
      bride.hug();
    },bride.name);

    for(Thread thread:threads) thread.start();


  }

}

class MarryPhaser extends Phaser {

  @Override
  protected boolean onAdvance(int phase, int registeredParties) {
    switch (phase){
      case 0:
        System.out.println("所有人到达，registeredParties="+registeredParties);
        return false;
      case 1:
        System.out.println("所有人吃饭，registeredParties="+registeredParties);
        return false;
      case 2:
        System.out.println("所有人离开，registeredParties="+registeredParties);
        return false;
      case 3:
        System.out.println("新郎新娘拥抱，registeredParties="+registeredParties);
        return true;
        default:
          return true;
    }

  }
}
