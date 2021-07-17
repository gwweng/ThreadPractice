package com.thread.practice.juc.programs.program2;

import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program2_02 {
    public static void main(String[] args) {
        MyContainer2<String> c = new MyContainer2<>();
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
        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    c.put(Thread.currentThread().getName() + "" + j);
                }
            },"p"+i).start();
        }
    }
}
