package com.mashibing.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hugangquan
 * @date 2020/11/15 21:51
 */
public class DisruptorTest03 {

    public static void main(String[] args) {

        //bufferSize must be a power of 2
        //指定生产者类型和等待策略
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(new MyEventFactory(),8, Executors.defaultThreadFactory(),
                ProducerType.SINGLE,new BusySpinWaitStrategy());
        disruptor.handleEventsWith(new MyEventHandler());
        disruptor.start();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i=0;i<50;i++){
            executorService.submit(()->{
                Random random = new Random();
                for(int j=0;j<100;j++){
                   disruptor.publishEvent((event, sequence) -> event.setNum(random.nextInt(1000)));
               }
            });
        }
    }

}
