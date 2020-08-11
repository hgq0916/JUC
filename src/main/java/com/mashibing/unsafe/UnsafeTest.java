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
    Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
    declaredField.setAccessible(true);
    Unsafe unsafe = (Unsafe) declaredField.get(null);
    System.out.println(unsafe);
  }

}
