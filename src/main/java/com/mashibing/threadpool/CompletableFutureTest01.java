package com.mashibing.threadpool;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author hugangquan
 * @date 2020/10/29 17:40
 */
public class CompletableFutureTest01 {

    public static void main(String[] args) throws IOException {
        CompletableFuture<Double> completableFuture = CompletableFuture.supplyAsync(CompletableFutureTest01::fetchPrice);

        completableFuture.thenAccept(price-> System.out.println("price:"+price));
        completableFuture.exceptionally(ex->{
            ex.printStackTrace();
            return null;
        });
        System.in.read();
    }


    public static double fetchPrice(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(Math.random()<0.3){
            throw new RuntimeException("获取价格失败");
        }

        return 5+Math.random()*20;
    }

}
