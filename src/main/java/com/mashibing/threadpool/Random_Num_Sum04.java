package com.mashibing.threadpool;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 求100个万随机数的总和,使用forkjoin
 * @author hugangquan
 * @date 2020/10/30 11:46
 */
public class Random_Num_Sum04 {

    public static final int MAX_NUM = 5000;

    public static long[] nums = new long[10000_0000];


    static {
        Random random = new Random();

        for(int i=0;i<nums.length;i++){
            nums[i] = random.nextInt(10000);
        }

    }

    public static ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static void main(String[] args) {

        long time1 = System.currentTimeMillis();

        MyTask01 myTask01 = new MyTask01(0, nums.length);
        Long sum = forkJoinPool.invoke(myTask01);

        System.out.println(sum);

        long time2 = System.currentTimeMillis();

        System.out.println("总耗时："+(time2-time1));

    }

}

class MyTask01 extends RecursiveTask<Long> {

    int start;
    int end;

    public MyTask01(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if((end-start)<=Random_Num_Sum04.MAX_NUM){
            long sum = 0;
            for(int i=start;i<end;i++){
                sum += Random_Num_Sum04.nums[i];
            }
            return sum;
        }else {
            int middle = (end + start)/2;
            MyTask01 subTask1 = new MyTask01(start,middle);
            MyTask01 subTask2 = new MyTask01(middle,end);
            long sum1 = subTask1.fork().join();
            long sum2 = subTask2.fork().join();

            return sum1+sum2;
        }
    }


}