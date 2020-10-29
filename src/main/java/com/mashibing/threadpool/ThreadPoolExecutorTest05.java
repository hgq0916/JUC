package com.mashibing.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 * @author hugangquan
 * @date 2020/10/29 18:40
 */
public class ThreadPoolExecutorTest05 {

   final static AtomicInteger threadCount = new AtomicInteger(0);

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
                new ArrayBlockingQueue<>(2),(runnable)->{

            return new Thread(runnable,"myThread"+threadCount.getAndIncrement());
        },new ThreadPoolExecutor.AbortPolicy());

        for(int i=0;i<10;i++){
            threadPoolExecutor.submit(new MyRunner(i));
        }
        threadPoolExecutor.shutdown();
    }


    private static class MyRunner implements Runnable,Comparable<MyRunner>{

        private int priority;

        public MyRunner(int priority){
            this.priority = priority;
        }

        @Override
        public int compareTo(MyRunner myRunner) {
            return this.priority>myRunner.priority?1:-1;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" 任务"+ priority +"执行");
        }
    }

}
