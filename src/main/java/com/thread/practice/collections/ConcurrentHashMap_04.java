package com.thread.practice.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class ConcurrentHashMap_04 {
    static Map<UUID, UUID> m = new ConcurrentHashMap<>();

    static int count = Constants.COUNT;

    static UUID[] keys = new UUID[count];

    static UUID[] values = new UUID[count];

    static final int THREAD_COUNT = Constants.THREAD_COUNT;

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread{
        int start;
        int gap = count / THREAD_COUNT;

        MyThread(int start){
            start = start;
        }

        @Override
        public void run() {
            for (int i = start ; i < start+gap; i++) {
                m.put(keys[i],values[i]);
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ConcurrentHashMap_04.MyThread(i*(count/THREAD_COUNT));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("put操作："+(end - start));


        start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    m.get(keys[j]);
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Map大小："+m.size());
        System.out.println("get操作："+ (end - start));

    }
}
