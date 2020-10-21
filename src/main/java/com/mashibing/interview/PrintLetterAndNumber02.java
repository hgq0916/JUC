package com.mashibing.interview;

/**
 * @author hugangquan
 * @date 2020/10/21 17:28
 */

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 两个线程交替输出A1B2C3D4...
 * 方法1：wait notify
 */
public class PrintLetterAndNumber02 {

    private static  Object lock = new Object();

    private static volatile boolean flag = true;

    public static void main(String[] args) {

        char[] letters = "ABCDEFGHJ".toCharArray();
        char[] numbers = "123456789".toCharArray();
        final AtomicReference<Thread> t1 = new AtomicReference<>();
        final AtomicReference<Thread> t2 = new AtomicReference<>();

        t1.set(new Thread(()->{
                    synchronized (lock){
                        for(int i=0;i<letters.length;i++){
                            System.out.println(letters[i]);
                            flag = false;
                            lock.notify();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        lock.notify();
                    }
                }));
        t2.set(new Thread(()->{
            synchronized (lock){
                if(flag){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=0;i<numbers.length;i++){
                    System.out.println(numbers[i]);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }));

        t1.get().start();
        t2.get().start();

    }

}
