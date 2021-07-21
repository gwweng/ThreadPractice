package com.thread.practice.juc.executor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/21
 * @desc
 */
public class OutOfTIme {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(),1);
        TimeUnit.SECONDS.sleep(1);
        timer.schedule(new ThrowTask(),1);
        TimeUnit.SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {
        public void run() {
           // throw  new RuntimeException();
            System.out.println("111");
        }
    }
    //最终程序会抛出 java.lang.IllegalStateException: Timer already cancelled.错误，说明
    //TimerTask并不能很好的处理异常，程序在1秒后就结束了整个Timer，所以再调用Schedule时就报错误了
}
