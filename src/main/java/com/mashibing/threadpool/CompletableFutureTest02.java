package com.mashibing.threadpool;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author hugangquan
 * @date 2020/10/29 17:40
 */
public class CompletableFutureTest02 {

    public static void main(String[] args) throws IOException {
       CompletableFuture<String> codeFuture = CompletableFuture.supplyAsync(()->{
           return queryCode("中国石油");
       });
        codeFuture.thenApplyAsync((code)->{
            System.out.println("code:"+code);
            return fetchPrice(code);
        });
        codeFuture.thenAccept(price->{
            System.out.println("price:"+price);
        });
        codeFuture.exceptionally(ex->{
            ex.printStackTrace();
            return null;
        });
        System.in.read();
    }

    private static String queryCode(String name) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "2374932472";
    }


    public static double fetchPrice(String code){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Math.random()<0.3){
            throw new RuntimeException("获取价格失败");
        }
        return Math.random()*20;
    }

}
