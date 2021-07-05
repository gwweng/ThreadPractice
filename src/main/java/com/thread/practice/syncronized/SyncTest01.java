package com.thread.practice.syncronized;

/**
 *
 * syncronized已经具备原子性和可见性，可重入性
 * 所以加了syncronized就不需要再加volatile
 * @author gengweiweng
 * @time 2021/7/5
 * @desc
 */
public class SyncTest01 implements Runnable {
    private /*volatile*/ int count = 100;


    @Override
    public  /*synchronized*/void run() {
        count --;
        System.out.println(Thread.currentThread().getName() + "   count = "+ count);
    }

    public static void main(String[] args) {
        SyncTest01 t = new SyncTest01();
        for (int i=0; i<100 ; i++){
            new Thread(t,"Thread" + i).start();
        }
    }
}
