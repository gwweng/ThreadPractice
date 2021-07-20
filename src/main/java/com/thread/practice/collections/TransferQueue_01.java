package com.thread.practice.collections;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author gengweiweng
 * @time 2021/7/19
 * @desc
 */
public class TransferQueue_01 {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.transfer("aaa");

    }
}
