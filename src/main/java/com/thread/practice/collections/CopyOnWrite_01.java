package com.thread.practice.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class CopyOnWrite_01 {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];
        for (int i=0 ; i<100; i++){
            ths[i] = new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    list.add("a"+r.nextInt(100000));
                }
            });
        }
        runAndComputeTime(ths);
    }

    static void runAndComputeTime(Thread[] ths){
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t->t.start());
        Arrays.asList(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long s2 = System.currentTimeMillis();
        System.out.println(s2 -s1);
    }
}
