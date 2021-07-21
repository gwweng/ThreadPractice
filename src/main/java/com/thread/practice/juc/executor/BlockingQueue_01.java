package com.thread.practice.juc.executor;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gengweiweng
 * @time 2021/7/21
 * @desc
 */
public class BlockingQueue_01 {
    // 阻塞队列
    final BlockingQueue<Runnable> queue = new SynchronousQueue<>();
    // 执行任务数
    final AtomicInteger completedTask = new AtomicInteger(0);
    // 拒绝任务数
    final AtomicInteger rejectedTask = new AtomicInteger(0);
    static long beginTime;
    final static int count = 1000;

    // 自定义线程池
    final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 600, 30, TimeUnit.SECONDS, queue, Executors.defaultThreadFactory(), (task, executor) -> {
        BlockingQueue<Runnable> queue = executor.getQueue();
        while (true){
            if(executor.isShutdown()){
                throw new RejectedExecutionException("ThreadPoolExecutor has shut down!");
            }

            try {
                if(queue.offer(task, 5000, TimeUnit.MILLISECONDS)){
                    break;
                }
            }catch (InterruptedException e){
                throw new AssertionError(e);
            }
        }
    });


    public void start(){
        // 开启线程
        CountDownLatch latch = new CountDownLatch(count);
        CyclicBarrier barrier = new CyclicBarrier(count);
        for (int i=0 ; i< count ; i++){
            new Thread(new TestThread(latch,barrier)).start();
        }
    }
    public static void main(String[] args) {
        // 记录开始时间
        beginTime = System.currentTimeMillis();
        BlockingQueue_01 blockingQueue01 = new BlockingQueue_01();
        blockingQueue01.start();
    }

     class TestThread implements Runnable{
        private CountDownLatch latch;
        private CyclicBarrier barrier;

        public TestThread(CountDownLatch latch, CyclicBarrier barrier) {
            this.latch = latch;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // barrier的值到达1000才能执行任务
                barrier.await();
            } catch (Exception e) {
                latch.countDown();
                System.out.println("被拒绝的任务数："+Thread.currentThread().getName()+"---"+rejectedTask.incrementAndGet());
            }

            executor.execute(new Task(latch));
        }
    }

    class Task implements Runnable{
        private CountDownLatch latch;
        public Task(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                // 模拟业务操作
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("执行的任务数"+Thread.currentThread().getName()+"---" + completedTask.incrementAndGet());
            System.out.println("任务耗时为："+ (System.currentTimeMillis() - beginTime) + "ms");
            latch.countDown();
        }
    }

}

