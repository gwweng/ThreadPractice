package com.thread.practice.juc.programs.program1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class Program1_09 {
    volatile List list = new ArrayList<>();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        // 程序成功
        Program1_09.soultion01();
    }

    public static void soultion01() {
    }
}
