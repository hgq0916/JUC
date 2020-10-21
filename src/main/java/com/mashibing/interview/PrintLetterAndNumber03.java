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
 * 方法1：Lock
 */
public class PrintLetterAndNumber03 {

    private static Lock lock = new ReentrantLock();

    private static volatile boolean flag = true;

    public static void main(String[] args) {

        char[] letters = "ABCDEFGHJ".toCharArray();
        char[] numbers = "123456789".toCharArray();
        final AtomicReference<Thread> t1 = new AtomicReference<>();
        final AtomicReference<Thread> t2 = new AtomicReference<>();

        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        t1.set(new Thread(()->{
            lock.lock();
            try{
                LockSupport.unpark(t2.get());
                for(int i=0;i<letters.length;i++){
                    System.out.println(letters[i]);
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        }));
        t2.set(new Thread(()->{
            LockSupport.park();
            lock.lock();
            try{
                for(int i=0;i<numbers.length;i++){
                    System.out.println(numbers[i]);
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        }));

        t1.get().start();
        t2.get().start();

    }

}
