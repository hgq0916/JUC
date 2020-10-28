package com.mashibing.threadpool;

import java.util.concurrent.*;

/**
 * @author hugangquan
 * @date 2020/10/28 11:11
 */
public class CallableTest {

    private static class MyRunner implements Callable<String> {

        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(2);
            return "helloworld";
        }
    }

    public static void main(String[] args) {
        MyRunner myRunner = new MyRunner();
        Future<String> future = Executors.newSingleThreadExecutor().submit(myRunner);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
