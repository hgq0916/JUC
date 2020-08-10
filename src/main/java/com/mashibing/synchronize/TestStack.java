package com.mashibing.synchronize;

import java.awt.TextArea;

public class TestStack {

  private int cc;

  private static TestStack testStack;

  private static double dd=2;

  {
    System.out.println("custructor block");
  }

  static {
    testStack = new TestStack();
    System.out.println("static init");
    dd = 1.0;
  }

  public TestStack(){
    System.out.println("constructor init");
    cc = 9;
  }

  public void print(){
    int a = 3;
    int b = 4;
    long c = a + b;
    boolean f = c==7;
  }

  public void testLocalVar(){
    {
      int a = 10;
    }
    int b = 20;
  }

  public static int testStatic(){
    int a = 3;
    int b = 4;
    int c = a + b;
    boolean f = c==7;
    return c;
  }

  public static void main(String[] args) {
    TestStack testStack = new TestStack();
    testStack.print();
    TestStack.testStatic();
  }

}
