package com.mashibing.disruptor;

import com.lmax.disruptor.ExceptionHandler;

/**
 * @author hugangquan
 * @date 2020/11/15 22:19
 */
public class MyExceptionHandler implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
        ex.printStackTrace();
        System.out.println(ex.getLocalizedMessage()+sequence+event);
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        System.out.println(ex.getLocalizedMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        System.out.println(ex.getLocalizedMessage());
    }
}
