package com.mashibing.unsafe;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.unsafe.UnsafeTest
 * @Description: TODO
 * @date 2020/8/11 13:20
 */
public class UnsafeTest {

  public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
    //直接获取unsafe实例抛异常
    //Unsafe unsafe = Unsafe.getUnsafe();
    //通过反射获取unsafe类的实例
    //Field declaredField = Unsafe.class.getDeclaredFields()[0];

    //操作基本类型的变量
    operateBasicVar();
    //操作线程
    operateThread();
    //cas
    cas();
  }

  private static void cas() throws NoSuchFieldException {
    Data data = new Data();
    data.a = 10;
    data.b = false;
    Unsafe unsafe = getUnsafe();
    Field fieldA = Data.class.getDeclaredField("a");
    fieldA.setAccessible(true);
    long address = unsafe.objectFieldOffset(fieldA);
    unsafe.compareAndSwapInt(data,address,10,20);
    System.out.println("data.a:"+data.a);
  }

  private static void operateThread() throws NoSuchFieldException {

  }

  private static void operateBasicVar() {
    Unsafe unsafe = getUnsafe();
    long address = unsafe.allocateMemory(4L);
    unsafe.putInt(address,10);
    System.out.println(unsafe.getInt(address));
    unsafe.freeMemory(address);
    System.out.println(unsafe.getAddress(address));
  }

  public static Unsafe getUnsafe(){
    Field declaredField = null;
    try {
      declaredField = Unsafe.class.getDeclaredField("theUnsafe");
      declaredField.setAccessible(true);
      Unsafe unsafe = (Unsafe) declaredField.get(null);
      return unsafe;
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return null;
  }

}

class Data {
  int a;
  boolean b;
}
