package com.mashibing.interview;

/**
 * @author hugangquan
 * @date 2020/10/21 17:28
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 两个线程交替输出A1B2C3D4...
 * 方法1：TransferQueue
 */
public class PrintLetterAndNumber06 {

    public static void main(String[] args) throws Exception {

        char[] letters = "ABCDEFGHJ".toCharArray();
        char[] numbers = "123456789".toCharArray();
        final AtomicReference<Thread> t1 = new AtomicReference<>();
        final AtomicReference<Thread> t2 = new AtomicReference<>();

        TransferQueue transferQueue1 = new LinkedTransferQueue();
        TransferQueue transferQueue2 = new LinkedTransferQueue();

        final Object obj = new Object();

        t1.set(new Thread(()->{
            for(int i=0;i<letters.length;i++){
                try {
                    System.out.println(letters[i]);
                    transferQueue2.put(obj);
                    transferQueue1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }));
        t2.set(new Thread(()->{
            for(int i=0;i<numbers.length;i++){
                try {
                    transferQueue2.take();
                    System.out.println(numbers[i]);
                    transferQueue1.put(obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }));

        t1.get().start();
        t2.get().start();

    }

}
