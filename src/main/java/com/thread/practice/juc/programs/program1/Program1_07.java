package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用LockSupport
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program1_07 {
    volatile List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序成功
        Program1_07.soultion01();
    }
    static Thread t1 = null, t2 = null;
    public static void soultion01() {
        Program1_07 c = new Program1_07();
        t2 = new Thread(() -> {
            System.out.println("t2 start");
            if(c.size() != 5){
                LockSupport.park();
            }
            System.out.println("t2 end");
            // unpark使得t1继续执行
            LockSupport.unpark(t1);
        },"t2");

        t2.start();
        t1 = new Thread(() -> {
            System.out.println("t1 start");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add" + i);
                if(c.size() == 5){
                    LockSupport.unpark(t2);
                    // 为了让t2有机会执行，阻塞t1
                    LockSupport.park();
                }
            }
            System.out.println("t1 end");
        },"t1");
        t1.start();
    }
}
