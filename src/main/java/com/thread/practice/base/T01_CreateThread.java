package com.thread.practice.base;

import java.util.concurrent.*;

/**
 * 创建线程的几种方式
 * @author gengweiweng
 * @time 2021/7/4
 * @desc
 */
public class T01_CreateThread {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello myThread");
        }
    }


    static class MyRun implements Runnable{
        @Override
        public void run() {
            System.out.println("Hello myThread2");
        }
    }

    static class MyCall implements Callable{
        @Override
        public String call() throws Exception {
            System.out.println("Hello myThread3");
            return "success";
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1 - new Thread
        new MyThread().start();

        // 2-new Runnable
        new Thread(new MyRun()).start();

        // 3-lambda
        new Thread(() -> {
            System.out.println("lambda Thread run");
        }).start();

        // 4-new Callbale
        FutureTask<String> task = new FutureTask<>(new MyCall());
        Thread t = new Thread(task);
        t.start();
        System.out.println(task.get());


        // 5-Thread pool
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() ->{
            System.out.println("Hello ThreadPool");
        });
        service.shutdown();
    }
}
