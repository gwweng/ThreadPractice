package com.thread.practice.juc.forkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author gengweiweng
 * @time 2021/12/8
 * @desc
 */
public class CalTask extends RecursiveTask<Integer> {
    private int[] array;
    private int start;
    private int end;
    private int thresold;

    public CalTask(int[] array, int start, int end, int thresold) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.thresold = thresold;
    }

    @Override
    protected Integer compute() {
        if(end - start <= thresold){ // 小于单任务负载
            int sum = 0;
            for (int i = start ; i< end ; i++){
                sum += array[i];
            }
            return sum;
        }else {
            int middle = (end - start) / 2 + start;
            CalTask leftTask = new CalTask(array, start, middle, thresold);
            CalTask rightTask = new CalTask(array, middle, end, thresold);
            leftTask.fork();
            rightTask.fork();
            return leftTask.join() + rightTask.join();
        }
    }
}
