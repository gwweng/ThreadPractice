package com.thread.practice.juc.readwriteLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 读-共享锁，写-排他锁
 * 解决读线程加锁速度慢
 * @author gengweiweng
 * @time 2021/7/12
 * @desc
 */
public class ReadWriteLock_01 {
    // 单纯加锁
    static Lock lock = new ReentrantLock();
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock){
        try {
            lock.lock();
            Thread.sleep(1000);
            // 模拟读取操作
            System.out.println("read Over!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v){
        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        //Runnable readR = () -> read(lock);
        //Runnable writeR = () -> write(lock, new Random().nextInt());
        Runnable readR = () -> read(readLock);
        Runnable writeR = () -> write(writeLock, new Random().nextInt());


        for (int i=0; i<18 ; i++) {
            new Thread(readR).start();
        }

        for (int j=0; j<2; j++) {
            new Thread(writeR).start();
        }

    }

}
