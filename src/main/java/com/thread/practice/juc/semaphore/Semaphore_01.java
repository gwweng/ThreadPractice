package com.thread.practice.juc.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 信号灯
 * permits表示允许的数量
 * @author gengweiweng
 * @time 2021/7/16
 * @desc
 */
public class Semaphore_01 {
    public static void main(String[] args) {
        // 允许2个线程同时执行
        Semaphore s = new Semaphore(2,true);//第二个参数true表示公平锁
        new Thread(() -> {
            try {
                // 获取到permit就通行，否则阻塞
                s.acquire();
                System.out.println("当前可用许可数量：" + s.availablePermits());
                System.out.println("立即可用许可数量：" + s.drainPermits());
                System.out.println("等待许可数量的线程个数：" + s.getQueueLength());
                System.out.println("是否有线程在等待这个许可："+s.hasQueuedThreads());
                System.out.println("T1 running....");
                Thread.sleep(200);
                System.out.println("T1 running.....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                // 释放permit
                s.release();
            }
        }).start();

        new Thread(() -> {
            try {
                s.acquire();
                System.out.println("T2 running.....");
                Thread.sleep(200);
                System.out.println("T2 running.....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
