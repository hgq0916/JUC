package com.mashibing.threadpool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hugangquan
 * @date 2020/10/30 11:42
 */
public class WorkStealingPoolTest {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newWorkStealingPool();
        for(int i=0;i<10;i++){
            int finalI = i;
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName()+"-"+ finalI);
            });
        }
        //executorService.shutdown();
        System.in.read();
    }

}
