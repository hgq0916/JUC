package com.mashibing.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.juc.ConsumerAndProductor
 * @Description: TODO
 * @date 2020/8/12 18:05
 */
public class ConsumerAndProductor {

  public static void main(String[] args) {
    //MyList<Integer>
  }

}

class MyList<T> {

  ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  Lock readLock = readWriteLock.readLock();
  Lock writeLock = readWriteLock.writeLock();

  private Object[] objs;

  private volatile int size = 0;

  public MyList(int len){
    objs = new Object[len];
  }

  /*public T put(int index ,T value){
    if(index>=size) throw new ArrayIndexOutOfBoundsException();
    Object oldV = null;
    if(objs[index] != null) oldV = objs[index];
    objs[index] = value;
    return (T) oldV;
  }*/

  public void put(T value){
    try{
      writeLock.lock();
      if(size >= objs.length) throw new IllegalStateException("array is full");
      objs[size++] = value;
    }finally {
      writeLock.unlock();
    }
  }

  public T get(int index){
    try{
      readLock.lock();
      if(index>=size) throw new ArrayIndexOutOfBoundsException();
      return (T) objs[index];
    }finally {
      readLock.unlock();
    }
  }

  public int getCount(){
    try{
      readLock.lock();
      return size;
    }finally {
      readLock.unlock();
    }
  }

}