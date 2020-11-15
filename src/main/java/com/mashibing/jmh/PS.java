package com.mashibing.jmh;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author hugangquan
 * @date 2020/11/15 21:03
 * 判断一个数是否是质数
 */
public class PS {

    private static List<Integer> data = new ArrayList<>();

    static {
        Random random = new Random();
        for(int i=0;i<10000;i++){
            data.add(1000000+random.nextInt(1000000));
        }
    }

    static void forEach(){
        data.forEach(d->isPrime(d));
    }

    static void parallel(){
        data.parallelStream().forEach(PS::isPrime);
    }

    static boolean isPrime(int num){
        for(int i=2;i<num/2;i++){
            if(num%i == 0) return false;
        }
        return true;
    }

}
