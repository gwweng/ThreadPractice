package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program1_06 {
    volatile List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序成功
        Program1_06.soultion01();
    }

    public static void soultion01(){
        Program1_06 c = new Program1_06();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println("t2 启动");
            if(c.size() != 5){
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2结束");
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add" + i);
                if(c.size() == 5){
                    // 打开latch使得t2得以执行
                    latch.countDown();
                    // 给t1上门栓，让t2有机会运行
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();
    }
}
