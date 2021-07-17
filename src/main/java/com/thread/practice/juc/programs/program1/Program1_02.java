package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author gengweiweng
 * @time 2021/7/16
 * @desc
 */
public class Program1_02 {
    volatile List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序失败
        //相比01程序，list使用volatile修饰
        //但是此程序仍然无法成功，因为volatile修饰基本类型值才有用，无法感知引用类型变量的值改变
        Program1_02.soultion01();
    }


    public static void soultion01(){
        Program1_02 c = new Program1_02();
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
