package com.mashibing.threadpool;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 求100个万随机数的总和,使用forkjoin
 * @author hugangquan
 * @date 2020/10/30 11:46
 */
public class Random_Num_Sum03 {

    public static final int MAX_NUM = 5000;

    public static int[] nums = new int[10000_0000];

    public static ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static void main(String[] args) {

        Random random = new Random();

        for(int i=0;i<nums.length;i++){
            nums[i] = random.nextInt(100000);
        }

        long time1 = System.currentTimeMillis();

        for(int i=0;i<nums.length;i = i+MAX_NUM){
            forkJoinPool.execute(new MyTask(i,i+MAX_NUM));
        }

        long time2 = System.currentTimeMillis();

        System.out.println("总耗时："+(time2-time1));

    }

}

class MyTask extends RecursiveAction {

    int start;
    int end;

    public MyTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if((end-start)<=Random_Num_Sum03.MAX_NUM){
            int sum = 0;
            for(int i=start;i<end;i++){
                sum += Random_Num_Sum03.nums[i];
            }
            System.out.println("计算结果："+sum);
        }else {
            int middle = (end - start)/2;
            MyTask subTask1 = new MyTask(start,middle);
            MyTask subTask2 = new MyTask(middle,end);
            subTask1.fork();
            subTask2.fork();
        }
    }


}