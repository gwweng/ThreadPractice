package com.thread.practice.juc.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gengweiweng
 * @time 2021/7/21
 * @desc
 */
public class ReentrantLock_04 {
    public static void main(String[] args) {
        final Common common = new Common();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                common.sub(i);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                common.sub2(i);
            }
        }).start();
        for (int i = 0; i < 50; i++) {
            common.sub3(i);
        }
    }
}

class Common{
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    private int should = 1;
    public   void sub(int i){
        try {
            lock.lock();
            while (should != 1) {   //用while而不用if可以避免虚假唤醒
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 5; j++) {
                System.out.println("sub  " + j + " loop of " + i);
            }
            should = 2;
            condition2.signal();
        }finally {
            lock.unlock();
        }
    }

    public   void sub3(int i){
        try {
            lock.lock();
            while (should != 3) {
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 10; j++) {
                System.out.println("main " + j + " loop of  " + i);
            }
            should = 1;
            condition1.signal();
        }finally {
            lock.unlock();
        }
    }

    public   void sub2(int i){
        try {
            lock.lock();
            while(should != 2) {
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 15; j++) {
                System.out.println("sub2 " + j + " loop of  " + i);
            }
            should = 3;
            condition3.signal();
        }finally {
            lock.unlock();
        }
    }
}
