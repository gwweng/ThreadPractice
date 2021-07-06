package com.thread.practice.syncronized;

import java.util.concurrent.TimeUnit;

/**
 * 锁优化案例2 - 锁对象
 *
 * @author gengweiweng
 * @time 2021/7/6
 * @desc
 */
public class SyncOptimize02 {
    // 锁定某对象o,如果o的属性发生改变，不影响锁的使用
    // 但是如果o变成另外一个对象，则锁定的对象发生改变
    // 应该避免将锁定对象的引用变成另外的对象
    final Object o = new Object();
    void m(){
        synchronized (o){
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        SyncOptimize02 syncOptimize02 = new SyncOptimize02();
        new Thread(syncOptimize02::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(syncOptimize02::m, "t2");

        // 锁对象发生改变，所以t2线程得以执行，如果注释掉这句话，线程2将永远得不到执行机会
       // syncOptimize02.o = new Object();

        t2.start();

    }
}
