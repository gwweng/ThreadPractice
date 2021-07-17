package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 使用semaphore实现
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program1_08 {
    volatile List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序成功
        Program1_08.soultion01();
    }

    public static void soultion01() {
        Semaphore s = new Semaphore(1);
        Program1_08 c = new Program1_08();
        Thread t2 = new Thread(() -> {
            try {
                s.acquire();
                System.out.println("t2 start");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(c.size() == 5){
                System.out.println("t2 end");
            }
            s.release();
        },"t2");
        new Thread(() -> {
            System.out.println("t1 start");
            try {
                s.acquire();
                for (int i =0 ; i<5 ; i++){
                    c.add(new Object());
                    System.out.println("add"+i);
                }
                s.release();
                t2.start();
                // 释放t1的CPU控制权给t2
                t2.join();
                s.acquire();
                for (int i =5 ; i<10 ; i++){
                    c.add(new Object());
                    System.out.println("add"+i);
                }
                s.release();
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();


    }
}
