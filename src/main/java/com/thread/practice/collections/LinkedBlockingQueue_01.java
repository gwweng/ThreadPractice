package com.thread.practice.collections;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class LinkedBlockingQueue_01 {
    static BlockingQueue<String> strs = new LinkedBlockingQueue<>();

    static Random r = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                    try {
                        strs.put("a"+i);// 如果满了就会等待
                        TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        },"p1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true){
                    try {
                        System.out.println(Thread.currentThread().getName() + ":"+ strs.take());// 如果空了就会等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"c"+i).start();
        }
    }

}
