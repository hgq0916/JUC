package com.mashibing.threadpool;

import java.util.concurrent.*;

/**
 * @author hugangquan
 * @date 2020/10/28 11:22
 */
public class FutrueTaskTest03 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask task = new FutureTask(()->{

            TimeUnit.SECONDS.sleep(2);
            return "helloworld";
        });
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(task);
        //task.run();

        System.out.println("sddsfdsf");
        try {
            Object o = task.get(1, TimeUnit.SECONDS);
            System.out.println(o);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(task.isDone());
        System.out.println(task.get());
        executorService.shutdown();
    }

}
