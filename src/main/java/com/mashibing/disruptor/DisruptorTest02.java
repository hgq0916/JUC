package com.mashibing.disruptor;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

/**
 * @author hugangquan
 * @date 2020/11/15 21:51
 */
public class DisruptorTest02 {

    public static void main(String[] args) {

        //bufferSize must be a power of 2
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(new MyEventFactory(),8, Executors.defaultThreadFactory());
        disruptor.handleEventsWith(new MyEventHandler());
        disruptor.start();

        disruptor.publishEvent(((event, sequence) -> event.setNum(10)));

        /*disruptor.publishEvent(new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent event, long sequence) {
                event.setNum(1000);
            }
        });*/

        //RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

       /* for(int i=0;i<20;i++){
            long sequence = ringBuffer.next();
            LongEvent longEvent = ringBuffer.get(sequence);
            longEvent.setNum(i);
            ringBuffer.publish(sequence);
        }*/
    }

}
