package com.thread.practice.juc.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author gengweiweng
 * @time 2021/7/16
 * @desc
 */
public class LockSupport_03 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i=0 ; i<10 ; i++){
                System.out.println(i);
                if(i == 0 || i == 8){
                    LockSupport.park();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        // 实践证明：连续调用2次park就会使线程永远无法被唤醒
        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
    }
}
