package com.thread.practice.collections;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue本质上是一个PriorityQueue
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class DelayQueue_01 {
    static class MyTask implements Delayed{
        String name;
        long runningTime;
        @Override
        public int compareTo(Delayed o) {
            if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)){
                return -1;
            }else if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)){
                return 1;
            }else {
                return 0;
            }
        }

        public MyTask(String name, long runningTime) {
            this.name = name;
            this.runningTime = runningTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return name+" "+ runningTime;
        }
    }
    static BlockingQueue<MyTask> tasks = new DelayQueue<>();
    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask("t1",now + 1000);
        MyTask t2 = new MyTask("t2",now + 2000);
        MyTask t3 = new MyTask("t3",now + 3000);
        MyTask t4 = new MyTask("t4",now + 4000);
        MyTask t5 = new MyTask("t5",now + 500);

        tasks.put(t1);
        tasks.put(t2);
        tasks.put(t3);
        tasks.put(t4);
        tasks.put(t5);

        System.out.println(tasks);
        for (int i = 0; i < 5; i++) {
            System.out.println(tasks.take());
        }
    }
}
