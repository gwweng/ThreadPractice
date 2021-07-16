package com.thread.practice.juc.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * juc底层工具类
 * 用于创建锁和其他同步工具类的基本线程阻塞原语
 * AQS（AbstractQueuedSynchronizer）是通过LockSupport的park和unpark实现线程阻塞和唤醒的
 * @author gengweiweng
 * @time 2021/7/16
 * @desc
 */
public class LockSupport_01 {
    public static void main(String[] args) {


        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5) {
                    // 启用LockSupport的park方法阻塞当前线程，没有加锁的限制
                    LockSupport.park();
                }
            }
        });
        thread.start();
    }
}
