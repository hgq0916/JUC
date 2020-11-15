package com.mashibing.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author hugangquan
 * @date 2020/11/15 21:50
 */
public class MyEventHandler implements EventHandler<LongEvent> {

    private String name;

    public MyEventHandler(){}

    public MyEventHandler(String name){
        this.name = name;
    }


    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        if(endOfBatch){
            int i = 1 / 0;
        }
        System.out.println(Thread.currentThread().getName()+","+"name:"+name+",num:"+event.getNum()+",sequence:"+sequence+",endOfBatch:"+endOfBatch);
    }
}
