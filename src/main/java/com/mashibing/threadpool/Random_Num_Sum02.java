package com.mashibing.threadpool;

import java.util.Arrays;
import java.util.Random;

/**
 * 求100个万随机数的总和
 * @author hugangquan
 * @date 2020/10/30 11:46
 */
public class Random_Num_Sum02 {

    public static void main(String[] args) {

        int[] nums = new int[1000_0000];

        Random random = new Random();

        for(int i=0;i<nums.length;i++){
            nums[i] = random.nextInt(10000);
        }

        long time1 = System.currentTimeMillis();

        int sum = 0;

        for(int i=0;i<nums.length;i++){
            sum += nums[i];
        }

        System.out.println(sum);

        long time2 = System.currentTimeMillis();

        System.out.println("总耗时："+(time2-time1));

    }

}
