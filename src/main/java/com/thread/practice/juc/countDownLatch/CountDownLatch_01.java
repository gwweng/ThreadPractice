package com.thread.practice.juc.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDown（倒数），Latch（门栓），顾名思义就是倒计数后门栓就开了
 * 相比使用join方法，有以下优点
 * 1.join必须线程结束了才能控制，但是countDownLatch可以灵活的countDown
 * 2.join只能线程结束后自动往前走，而countDownLatch可以控制什么时候门栓往前走
 * @author gengweiweng
 * @time 2021/7/12
 * @desc
 */
public class CountDownLatch_01 {
    private static void usingCountDownLatch() {
        Thread[] threads = new Thread[100];
        CountDownLatch countDownLatch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j <= 1000; j++) {
                    result += j;
                }
                // 计数值减1
                countDownLatch.countDown();
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            System.out.println("start latch");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end latch");
    }

    private static void usingJoin() {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j <= 1000; j++) {
                    result += j;
                }
                System.out.println(result);
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

        System.out.println("start join");
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end join");
    }

    public static void main(String[] args) {
        //usingJoin();
        usingCountDownLatch();
    }
}
