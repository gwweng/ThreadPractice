package com.thread.practice.juc.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * 1.支持可重入，相比Syncronized可以使用tryLock进行尝试解锁，Syncronized会阻塞，而ReentrantLock可以决定是否要wait
 * 2.可以被打断加锁（查看程序02）
 * 3.支持公平锁与非公平锁（参数传true）
 * @author gengweiweng
 * @time 2021/7/12
 * @desc
 */
public class ReentrantLock_01 {
    Lock lock = new ReentrantLock();

    void m1(){
        try {
            lock.lock();
            for (int i=0; i<3; i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    void m2(){
        boolean locked = false;

        try {
            locked = lock.tryLock(5,TimeUnit.SECONDS);
            System.out.println("m2..."+ locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) lock.unlock();
        }
    }



    public static void main(String[] args) {
        ReentrantLock_01 r1 = new ReentrantLock_01();
        new Thread(r1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}
