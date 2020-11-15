package com.mashibing.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author hugangquan
 * @date 2020/11/15 21:45
 */
public class MyEventFactory implements EventFactory<LongEvent> {


    @Override
    public LongEvent newInstance() {

        return new LongEvent();
    }
}
