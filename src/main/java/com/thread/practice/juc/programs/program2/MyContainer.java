package com.thread.practice.juc.programs.program2;

import java.util.LinkedList;

/**
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class MyContainer<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    // 最多10个元素
    final private int MAX = 10;

    private int count;

    // 生产者
    public synchronized void put(T t){
        while (lists.size() == MAX){
            // 为什么用while而不是if
            // 因为使用if就不会再判断一次，方法会继续往下执行，假如在wait方法之后另外一个方法又put一个元素，就会造成数据错误
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(t);
        ++count;
        this.notifyAll(); // 通知消费者线程进行消费（但是同时也会通知到生产者线程，优化：查看MyContainer2）
    }

    // 消费者
    public synchronized T get(){
        T t = null;
        while (lists.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count --;
        this.notifyAll(); // 通知生产者进行生产
        return  t;
    }
}
