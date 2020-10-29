package com.mashibing.threadpool;

import java.util.concurrent.*;

/**
 * 使用无界队列创建线程
 * @author hugangquan
 * @date 2020/10/29 18:40
 */
public class ThreadPoolExecutorTest04 {

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
                new LinkedBlockingDeque<>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        for(int i=0;i<5;i++){
            int finalI = i;
            threadPoolExecutor.submit(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 任务"+ finalI +"执行");
            });
        }
        threadPoolExecutor.shutdown();
    }

}
