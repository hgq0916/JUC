package com.mashibing.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hugangquan
 * @date 2020/10/30 11:38
 */
public class FixedThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i=0;i<10;i++){
            int finalI = i;
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+"-"+ finalI);
            });
        }
        executorService.shutdown();
    }
}
