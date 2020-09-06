package com.mashibing.reference;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.reference.WeakReferenceTest
 * @Description: 弱引用
 * @date 2020/8/14 16:19
 */
public class WeakReferenceTest {

  public static void main(String[] args) throws IOException, InterruptedException {

    T t1 = new T();
    WeakReference<T> weakReference = new WeakReference<>(t1);

    System.out.println(weakReference.get());

    t1 = null;//没有引用指向t1对象了，t1对象会被回收

    System.gc();

    TimeUnit.SECONDS.sleep(1);

    System.out.println(weakReference.get());

    System.in.read();
  }

}
