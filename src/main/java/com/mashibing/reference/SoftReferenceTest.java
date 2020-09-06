package com.mashibing.reference;

import java.lang.ref.SoftReference;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.reference.SoftReferenceTest
 * @Description: 软引用测试,用于缓存，当内存不够用的时候会被gc回收
 * @date 2020/8/14 16:11
 */
public class SoftReferenceTest {

  public static void main(String[] args) {

    //分配10m堆内存
    SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024*1024*10]);

    System.out.println(softReference.get());

    System.gc();
    System.out.println(softReference.get());

    //分配12m堆内存,堆内存不够用了，gc会把之前的软引用对象回收
    SoftReference<byte[]> softReference1 = new SoftReference<>(new byte[1024*1024*12]);

    System.out.println(softReference.get());
    System.out.println(softReference1.get());

  }

}
