package com.thread.practice.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程不安全的ArrayList
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class ArrayList_05 {
    static List<String> tickets = new ArrayList<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编码"+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
               while (tickets.size() > 0){
                   System.out.println("销售了--"+tickets.remove(0));
               }
            }).start();
        }
    }
}
