package com.thread.practice.syncronized;

import java.util.concurrent.TimeUnit;

/**
 * 模拟银行账户
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 会容易导致脏读问题
 * @author gengweiweng
 * @time 2021/7/5
 * @desc
 */
public class SyncTest02 {
    String name;
    double balance;

    public synchronized void set(String name, double balance){
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public /*synchronized*/ double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        SyncTest02 a = new SyncTest02();
        new Thread(() -> a.set("zhangsan", 100.0)).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangsan"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangsan"));
    }

}
