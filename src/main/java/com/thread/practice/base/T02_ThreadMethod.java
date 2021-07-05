package com.thread.practice.base;

/**
 * 学会使用线程的方法
 *
 * 一、线程状态：
 * 1.新建（new的时候）
 * 2.就绪（调用start时扔到等待队列中）
 * 3.运行（CPU执行时）
 * 4.阻塞(TimeWaiting等待，Waiting等待，Blocked阻塞)
 * 5.结束
 *
 * 二、线程相关方法
 * 1.o.wait(),o.join(),LockSupport.part()会使得线程进入Waiting状态
 * 2.o.notify()，o.notifyAll(),LockSupport.unpark()会使得线程进入Running状态
 * 3.o.wait(time),Thread.sleep(time),t.jion(time),LockSupport.parkNanos()，LockSupport.parkUntil() 会使得线程进入TimedWaiting状态
 *
 * 线程挂起：线程从running状态被扔回去等待
 * @author gengweiweng
 * @time 2021/7/5
 * @desc
 */
public class T02_ThreadMethod {
    /**
     * 线程状态：Running -> TimeWaiting
     * Sleep -> 线程休眠，当前线程暂停一段时间让给别的线程去运行，指定睡眠时间自动复活
     */
    static void testSleep(){
        new Thread(() ->{
            for (int i=0 ; i<100 ; i++){
                System.out.println("A" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 线程状态：Running -> Ready
     * Yield - 将当前线程暂停并放回等待队列中，让出来CPU，但是仍然有可能被调度算法又拿回去继续执行，当然更大的可能性是把原来等待的那些
     * 线程拿出来执行
     */
    static void testYield(){
        new Thread(() -> {
           for (int i=0 ; i<100 ;i++){
               System.out.println("A" + i);
               if(i % 10 == 0) Thread.yield();
           }
        }).start();

        new Thread(() -> {
           for (int j=0 ; j<100 ; j++){
               System.out.println("B" + j);
               if(j % 10 == 0) Thread.yield();
           }
        }).start();
    }

    /**
     * 线程状态：Running -> Waiting
     */
    static void testJoin(){
        Thread t1 = new Thread(() -> {
            for (int i=0 ; i<100; i++){
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
           try {
               t1.join();
           }catch (InterruptedException e){
               e.printStackTrace();
           }

           for (int i=0; i<100; i++){
               System.out.println("B" + i);
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });

        t1.start();
        t2.start();


    }

    /**
     * join - 在当前线程加入调用join的线程，本线程等待
     */

    public static void main(String[] args) {
        //testSleep();
        //testYield();
        testJoin();
        System.out.println("Hello world");
    }
}
