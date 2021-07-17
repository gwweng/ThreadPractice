package com.thread.practice.juc.programs.program2;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 区分生产者和消费者线程进行唤醒
 * @author gengweiweng
 * @time 2021/7/17
 * @desc
 */
public class MyContainer2<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    // 最多10个元素
    final private int MAX = 10;

    private int count;

    private Lock lock = new ReentrantLock();
    // 用于精确唤醒指定哪些线程
    // Lock和Condition本质是，Syncronized里调用notify和wait时只有一个等待队列，但Lock.newCondition会多出等待队列
    private Condition producer = lock.newCondition();

    private Condition consumer = lock.newCondition();

    // 生产者
    public  void put(T t){
        try {
            lock.lock();
            while (lists.size() == MAX){
                // 将当前线程进入producer的等待队列
                producer.await();
            }
            lists.add(t);
            ++count;
            // 唤醒consumer队列中的线程
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 消费者
    public  T get(){
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0){
               consumer.await();
            }
            t = lists.removeFirst();
            count --;
            producer.signalAll(); // 通知生产者进行生产
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return  t;
    }

}
