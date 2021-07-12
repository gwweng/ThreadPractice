package com.thread.practice.juc.reentrantLock;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gengweiweng
 * @time 2021/7/12
 * @desc
 */
public class ReentrantLock_02 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
           try {
               lock.lock();
               System.out.println("t1 start");
               TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
               System.out.println("t2 end");
           }catch (InterruptedException e){
               System.out.println("t1 停止等待");
           }finally {
                lock.unlock();
           }
        });

        t1.start();

        Thread t2 = new Thread(() -> {
           try {
               //lock.lock(); 这里使用lock方法会一直阻塞
               lock.lockInterruptibly();
               System.out.println("t2 start");
               TimeUnit.SECONDS.sleep(5);
               System.out.println("t2 end");
           }catch (InterruptedException e){
               System.out.println("t2 停止等待");
           }finally {
               lock.unlock();
           }
        });
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();// 打断t2的等待
    }
}
