package com.mashibing.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * @author hugangquan
 * @date 2020/11/15 21:09
 */
public class PSTest {

    @Benchmark
    @Threads(2)
    @Timeout(time = 1,timeUnit = TimeUnit.SECONDS)
    public void testForEach(){
        PS.forEach();
    }

}
