package com.thread.practice.juc.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 循环栅栏，这有一个栅栏，什么时候人满了就把栅栏推倒，人都放出去后栅栏又重新围起来，再来人，满了推倒之后继续
 * 使用场景：业务操作需要访问数据库，访问磁盘，访问网络，可以使用多线程并发方式比较快，此时可以考虑CyclicBarrier
 * @author gengweiweng
 * @time 2021/7/12
 * @desc
 */
public class CyclicBarrier_01 {
    public static void main(String[] args) {
        // CyclicBarrier cyclicBarrier = new CyclicBarrier(20);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> System.out.println("满人"));
        for (int i=0 ; i<20 ; i++){
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
