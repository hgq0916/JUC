package com.mashibing.threadpool;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * CompletableFuture串行执行
 * @author hugangquan
 * @date 2020/10/29 17:40
 */
public class CompletableFutureTest03 {

    public static void main(String[] args) throws IOException {
        CompletableFuture<String> cfSinaCode = CompletableFuture.supplyAsync(()->{
            return queryCode("中国石油","http://sina.com.cn");
        });
        CompletableFuture<String> from163Code = CompletableFuture.supplyAsync(()->{
            return queryCode("中国石油","http://163.com.cn");
        });

        CompletableFuture<Object> priceFuture = CompletableFuture.anyOf(cfSinaCode, from163Code);
        CompletableFuture<Double> priceFromSina = priceFuture.thenApplyAsync(code -> {
            return fetchPrice((String) code,"http://sina.com.cn");
        });
        CompletableFuture<Double> priceFrom163 =priceFuture.thenApplyAsync(code -> {
            return fetchPrice((String) code,"http://sina.com.cn");
        });

        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(priceFromSina, priceFrom163);

        objectCompletableFuture.thenAccept(price->{
            System.out.println("price:"+(double)price);
        });

        objectCompletableFuture.thenAccept(price->{
            System.out.println("price:"+(double)price);
        });

        objectCompletableFuture.exceptionally(ex->{
            ex.printStackTrace();
            return null;
        });

        System.in.read();

    }

    private static String queryCode(String name, String url) {

        System.out.println("从"+url+"获取证券代码");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "2374932472";
    }


    public static double fetchPrice(String code, String url){

        System.out.println("从"+url+"获取证券价格");
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
