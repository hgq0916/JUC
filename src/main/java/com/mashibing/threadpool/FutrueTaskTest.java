package com.mashibing.threadpool;

import java.util.concurrent.*;

/**
 * @author hugangquan
 * @date 2020/10/28 11:22
 */
public class FutrueTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask task = new FutureTask(()->{
            TimeUnit.SECONDS.sleep(2);
            return "futureTask";
        });
        Executors.newSingleThreadExecutor().submit(task);
        //task.run();
        System.out.println("sddsfdsf");
        System.out.println(task.get());
    }

}
