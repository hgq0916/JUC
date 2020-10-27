package com.mashibing.interview;

/**
 * @author hugangquan
 * @date 2020/10/21 17:28
 */

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替输出A1B2C3D4...
 * 方法1：自旋
 */
public class PrintLetterAndNumber04 {

    private static volatile boolean flag = true;

    public static void main(String[] args) {

        char[] letters = "ABCDEFGHJ".toCharArray();
        char[] numbers = "123456789".toCharArray();
        final AtomicReference<Thread> t1 = new AtomicReference<>();
        final AtomicReference<Thread> t2 = new AtomicReference<>();

        t1.set(new Thread(()->{
            for(int i=0;i<letters.length;i++){
                while (!flag);
                System.out.println(letters[i]);
                flag = false;
            }
        }));
        t2.set(new Thread(()->{
            for(int i=0;i<numbers.length;i++){
                while (flag);
                System.out.println(numbers[i]);
                flag = true;
            }
        }));

        t1.get().start();
        t2.get().start();

    }

}
