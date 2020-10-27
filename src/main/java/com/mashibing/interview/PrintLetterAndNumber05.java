package com.mashibing.interview;

/**
 * @author hugangquan
 * @date 2020/10/21 17:28
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 两个线程交替输出A1B2C3D4...
 * 方法1：PipedStream
 */
public class PrintLetterAndNumber05 {

    public static void main(String[] args) throws Exception {

        char[] letters = "ABCDEFGHJ".toCharArray();
        char[] numbers = "123456789".toCharArray();
        final AtomicReference<Thread> t1 = new AtomicReference<>();
        final AtomicReference<Thread> t2 = new AtomicReference<>();

        PipedInputStream pis1 = new PipedInputStream();
        PipedInputStream pis2 = new PipedInputStream();

        PipedOutputStream pos1 = new PipedOutputStream();
        PipedOutputStream pos2 = new PipedOutputStream();

        pis1.connect(pos2);
        pis2.connect(pos1);


        t1.set(new Thread(()->{
            for(int i=0;i<letters.length;i++){
                int rec = -1;
                try {
                    pis1.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(letters[i]);
                try {
                    if(rec == 65){
                        pos1.write(65);
                        pos1.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                pos1.write(66);
                pis1.close();
                pos1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        t2.set(new Thread(()->{
            try {
                pos2.write(65);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i=0;i<numbers.length;i++){
                int rec = -1;
                try {
                    rec = pis2.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(numbers[i]);
                try {
                    if(rec == 65){
                        pos2.write(65);
                        pos2.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                pos2.write(66);
                pis2.close();
                pos2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));

        t1.get().start();
        t2.get().start();

    }

}
