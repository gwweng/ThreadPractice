package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器，提供2个方法add，size，写2个线程
 * 线程1：添加10个元素到容器中
 * 线程2：实时监控元素个数，当个数到5时，线程2给出提示并结束
 * @author gengweiweng
 * @time 2021/7/16
 * @desc
 */
public class Program1_01 {
    List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序失败
        Program1_01.soultion01();
    }

    public static void soultion01(){
        Program1_01 c = new Program1_01();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add" + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t1 结束");
        }, "t1").start();

        new Thread(() ->{
            // 代码永远不会执行，原因如下
            // 1- 没有加同步，当t1线程的size更新的时候，还没有更新t2线程就读了
            // 2-t2线程永远也没有检测到，因为线程与线程之间不可见
            while (true){
               // System.out.println("size="+c.size());
                if(c.size() == 5){
                    break;
                }
            }
            System.out.println("t2 结束");
        }).start();
    }


}
