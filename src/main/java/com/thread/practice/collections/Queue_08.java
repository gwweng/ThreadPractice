package com.thread.practice.collections;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 为多线程取单元素天然而生
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class Queue_08 {
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编码"+i);
        }
    }

    //使用syncronized（tickets）判断size才是准确的
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true){
                   String s = tickets.poll();
                   if(s == null) break;
                   else System.out.println("销售了--"+s);
                }
            }).start();
        }
    }
}
