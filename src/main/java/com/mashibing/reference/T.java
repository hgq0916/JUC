package com.mashibing.reference;

/**
 * @author gangquan.hu
 * @Package: com.mashibing.reference.T
 * @Description: TODO
 * @date 2020/8/14 16:20
 */
public class T {

  @Override
  protected void finalize() throws Throwable {
    System.out.println("finalize");
  }
}
