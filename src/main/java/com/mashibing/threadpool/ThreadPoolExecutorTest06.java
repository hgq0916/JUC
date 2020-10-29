package com.mashibing.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 * @author hugangquan
 * @date 2020/10/29 18:40
 */
public class ThreadPoolExecutorTest06 {

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
        ThreadPoolExecutor threadPoolExecutor = new MyThreadPoolExcutor(1,2,10000,TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(8),(runnable)->{

            return new Thread(runnable,"myThread"+threadCount.getAndIncrement());
        },new ThreadPoolExecutor.AbortPolicy());

        for(int i=0;i<10;i++){
            threadPoolExecutor.submit(new MyRunner("task"+i));
        }
        threadPoolExecutor.shutdown();

    }


    private static class MyRunner implements Runnable{

        private String taskName;

        public MyRunner(String taskName){
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" 任务:"+ taskName +"执行");
        }
    }

    private static class MyThreadPoolExcutor extends ThreadPoolExecutor {

        public MyThreadPoolExcutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("before:"+t+","+r);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("after:"+t+","+r);
        }

        @Override
        protected void terminated() {
            System.out.println("线程池终止");
        }
    }

}
