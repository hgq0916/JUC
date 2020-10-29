package com.mashibing.threadpool;

import java.util.concurrent.*;

/**
 * 直接提交队列
 * @author hugangquan
 * @date 2020/10/29 18:40
 */
public class ThreadPoolExecutorTest02 {

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
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,2,10000,TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        for(int i=0;i<3;i++){
            int finalI = i;
            threadPoolExecutor.submit(()->{
                System.out.println("任务"+ finalI +"执行");
            });
        }
    }

}
