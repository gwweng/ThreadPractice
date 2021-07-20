package com.thread.practice.collections;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class ArrayBlockingQueue_01 {
    static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            strs.put("a"+i);

        }

         strs.put("aaa");// 满了会等待，程序阻塞
        strs.add("aaa");
        strs.offer("aaa");
        strs.offer("aaa",1, TimeUnit.SECONDS);
        System.out.println(strs);
    }
}
