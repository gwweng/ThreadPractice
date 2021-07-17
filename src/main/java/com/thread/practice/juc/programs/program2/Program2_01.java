package com.thread.practice.juc.programs.program2;

import java.util.concurrent.TimeUnit;

/**
 * 实现程序：写一个固定容量的同步容器，有put和get，getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program2_01 {
    public static void main(String[] args) {
        MyContainer<String> c = new MyContainer<>();
        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(c.get());
                }
            },"c"+i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    c.put(Thread.currentThread().getName() + "" + j);
                }
            },"p"+i).start();
        }

    }
}
