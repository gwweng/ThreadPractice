package com.thread.practice.juc.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author gengweiweng
 * @time 2021/7/16
 * @desc
 */
public class LockSupport_02 {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5){
                    LockSupport.park();
                }
                try {
                    // 当前线程睡眠1秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        // 实践证明，unpark可以在
        LockSupport.unpark(t);
    }
}
