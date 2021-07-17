package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program1_05 {
    volatile List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序成功
        // 与03的区别是在t1线程执行notify之后又紧接着调用wait阻塞t1线程
        Program1_05.soultion01();
    }

    public static void soultion01(){
        Program1_05 c = new Program1_05();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println("t2 start");
            if(c.size() != 5){
                try {
                    //
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            System.out.println("t1 start");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add"+i);
                if(c.size() == 5){
                    // 暂停t1线程
                    latch.countDown();
                }
                // 如果注释此段代码，t2线程的latch虽然打开，但是还没来的及获取size=5，t1就继续执行修改了size
                // 这种情况下countDownLatch不可行，如果非要使用latch,查看06程序的写法
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

    }
}
