package com.mashibing.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hugangquan
 * @date 2020/10/30 11:31
 */
public class SingleThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i=0;i<10;i++){
            int finalI = i;
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+"-"+ finalI);
            });
        }
        executorService.shutdown();
    }
}
