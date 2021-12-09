package com.thread.practice.juc.forkJoin;

import javafx.concurrent.Task;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

/**
 * @author gengweiweng
 * @time 2021/12/8
 * @desc 计算一个大List的元素之和
 */
public class ForkJoinTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] array = IntStream.rangeClosed(0, 1_00_000_000).toArray();
        // 一般解法
        int sum = 0;
        long start = System.currentTimeMillis();
        for (int i=0 ; i<array.length; i++){
            sum += array[i];
        }
        System.out.println("计算总和 = " + sum + "\n 计算耗时 = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CalTask task = new CalTask(array, 0, array.length, 1000);
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);
        sum = result.get();
        System.out.println("计算总和 = " + sum + "\n 计算耗时 = " + (System.currentTimeMillis() - start));

    }
}
