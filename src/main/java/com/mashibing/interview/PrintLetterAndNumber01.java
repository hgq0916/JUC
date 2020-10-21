package com.mashibing.interview;

/**
 * @author hugangquan
 * @date 2020/10/21 17:28
 */

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 两个线程交替输出A1B2C3D4...
 * 方法1：Locksupport
 */
public class PrintLetterAndNumber01 {

    public static void main(String[] args) {

        char[] letters = "ABCDEFGHJ".toCharArray();
        char[] numbers = "123456789".toCharArray();
        final AtomicReference<Thread> t1 = new AtomicReference<>();
        final AtomicReference<Thread> t2 = new AtomicReference<>();
        t1.set(new Thread(()->{
                    for(int i=0;i<letters.length;i++){
                        System.out.println(letters[i]);
                        LockSupport.unpark(t2.get());
                        LockSupport.park();
                    }
                }));
        t2.set(new Thread(()->{
            for(int i=0;i<numbers.length;i++){
                LockSupport.park();
                System.out.println(numbers[i]);
                LockSupport.unpark(t1.get());
            }
        }));

        t1.get().start();
        t2.get().start();

    }

}
