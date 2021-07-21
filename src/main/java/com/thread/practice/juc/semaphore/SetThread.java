package com.thread.practice.juc.semaphore;

/**
 * @author gengweiweng
 * @time 2021/7/21
 * @desc
 */
public class SetThread extends Thread {
    private Semaphore_02 semaphore_02;


    public SetThread(Semaphore_02 semaphore_02) {
        this.semaphore_02 = semaphore_02;
    }

    @Override
    public void run() {
        semaphore_02.set();
    }
}
