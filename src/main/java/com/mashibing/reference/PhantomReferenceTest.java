package com.mashibing.reference;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.reference.PhantomReferenceTest
 * @Description: 虚引用，用于管理堆外内存
 * @date 2020/8/14 17:06
 */
public class PhantomReferenceTest {

  private static ReferenceQueue<T> referenceQueue = new ReferenceQueue<>();

  public static void main(String[] args) throws IOException, InterruptedException {
    T t1 = new T();
    PhantomReference<T> phantomReference = new PhantomReference<>(t1,referenceQueue);

    System.out.println(phantomReference.get());

    new Thread(()->{
      while (true){
        Reference<? extends T> reference = referenceQueue.poll();
        System.out.println("reference :"+reference);
        System.out.println("reference value :"+(reference==null?null:reference.get()));
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

    t1 = null;

    System.gc();

    TimeUnit.SECONDS.sleep(1);

    System.gc();

    System.in.read();

  }

}
