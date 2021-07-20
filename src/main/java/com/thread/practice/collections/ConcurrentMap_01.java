package com.thread.practice.collections;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class ConcurrentMap_01 {
    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();

        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    map.put("a"+r.nextInt(100000),"a"+r.nextInt(100000));
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(map.size());
    }
}
