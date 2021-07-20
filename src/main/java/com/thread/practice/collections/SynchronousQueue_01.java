package com.thread.practice.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 *
 * SynchronousQueue在线程池中的应用比较广
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class SynchronousQueue_01 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strs = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        strs.put("aaa");// 阻塞等待消费者消费
        strs.put("bbb");
        strs.add("aaa");
    }
}
