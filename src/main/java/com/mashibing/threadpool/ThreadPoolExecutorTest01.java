package com.mashibing.threadpool;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author hugangquan
 * @date 2020/10/29 18:40
 */
public class ThreadPoolExecutorTest01 {

    public static void main(String[] args) {
        /**
         *      int corePoolSize,
        *      int maximumPoolSize,
        *      long keepAliveTime,
        *      TimeUnit unit,
        *      BlockingQueue<Runnable> workQueue,
        *      ThreadFactory threadFactory,
        *      RejectedExecutionHandler handler
         */
        BlockingQueue blockingQueue = new LinkedBlockingDeque(2);
        ThreadFactory threadFactory = (runnable)->{
            return new Thread(runnable);
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,60, TimeUnit.SECONDS,
                blockingQueue,threadFactory,new ThreadPoolExecutor.AbortPolicy());

        for(int i=0;i<8;i++){
            int finalI = i;
            threadPoolExecutor.submit(()->{
                System.out.println(Thread.currentThread().getName()+ finalI);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"获取价格："+23432.2342);
            });
        }



        threadPoolExecutor.shutdown();
    }

}
