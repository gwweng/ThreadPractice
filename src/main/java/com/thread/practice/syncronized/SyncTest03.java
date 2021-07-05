package com.thread.practice.syncronized;

import java.util.concurrent.TimeUnit;

/**
 * 异常锁
 * 程序执行过程中，如果出现异常，默认情况锁会被释放
 * 所以在并发处理时，有异常可能会发生不一致的情况
 * @author gengweiweng
 * @time 2021/7/5
 * @desc
 */
public class SyncTest03 {

    int count = 0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName() + " start");
        while (true){
            count ++;
            System.out.println(Thread.currentThread().getName() + " count = "+ count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(count == 5){
                int i = 1/0;// 此处抛出异常，锁会被释放，要想不被释放，可以捕获异常
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        SyncTest03 t = new SyncTest03();
        Runnable r = () -> t.m();
        new Thread(r,"t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r, "t2").start();
    }
}
