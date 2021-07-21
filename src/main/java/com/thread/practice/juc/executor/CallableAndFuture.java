package com.thread.practice.juc.executor;

import java.util.concurrent.*;

/**
 * @author gengweiweng
 * @time 2021/7/21
 * @desc
 */
public class CallableAndFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> "test");
        System.out.println("任务的执行情况："+future.get());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("HeartBeat...."),5,4,TimeUnit.SECONDS);
    }
}
