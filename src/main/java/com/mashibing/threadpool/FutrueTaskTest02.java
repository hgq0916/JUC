package com.mashibing.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author hugangquan
 * @date 2020/10/28 11:22
 */
public class FutrueTaskTest02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask task = new FutureTask(()->{

            try{
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                System.out.println("我被中断了");
            }

            return "sdfdsfs";
        });
        Executors.newSingleThreadExecutor().submit(task);
        //task.run();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!task.isDone() && !task.isCancelled()){
                task.cancel(true);
                System.out.println("cancelled");
            }
        }).start();
        System.out.println("sddsfdsf");
        System.out.println(task.get());
    }

}
