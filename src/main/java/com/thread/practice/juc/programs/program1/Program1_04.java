package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program1_04 {
    volatile List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序成功
        // 与03的区别是在t1线程执行notify之后又紧接着调用wait阻塞t1线程
        Program1_04.soultion01();
    }

    public static void soultion01(){
        Program1_04 c = new Program1_04();
        final Object lock = new Object();
        new Thread(() -> {
            System.out.println("t2 start");
            synchronized (lock){
                if(c.size() != 5){
                    try {
                        // 一直阻塞
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 end");
            }
            // 唤醒t1线程
            lock.notify();
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 start");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add" + i);
                    if(c.size() == 5){
                        lock.notify();
                    }
                    try {
                        // 释放锁让t2得以执行
                        lock.wait();
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("t1 end");
        }, "t1").start();
    }
}
