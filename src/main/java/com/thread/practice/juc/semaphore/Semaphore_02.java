package com.thread.practice.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Semaphore实现生产者消费者模式，并且限制生产者消费者
 * @author gengweiweng
 * @time 2021/7/21
 * @desc
 */
public class Semaphore_02 {
    // 十个生产者
    Semaphore setSemaphore = new Semaphore(10);
    // 十个消费者
     Semaphore getSemaphoe = new Semaphore(20);
    ReentrantLock lock = new ReentrantLock();
    // 生产者等待队列
    Condition setCondition = lock.newCondition();
    //  消费者等待队列
    Condition getCondition = lock.newCondition();

    // 只有4个盘子可以存放菜品
    volatile private  Object[] producePosition = new Object[4];


    public boolean isEmpty(){
        for (int i = 0; i < producePosition.length; i++) {
            if(producePosition[i] != null){
                return false;
            }
        }
        return true;
    }

    public boolean isFull(){
        return  !isEmpty();
    }

    public void get(){
        try {
            // 获取许可
            getSemaphoe.acquire();
            lock.lock();
            while (isEmpty()){
                System.out.println("菜碟子空了，客官们请稍等.....");
                getCondition.await();
            }
            for (int i = producePosition.length-1; i >=0 ; i--) {
                if(producePosition[i] != null){
                    System.out.println("菜品"+producePosition[i]+"上来了，客官"+Thread.currentThread().getName()+"请慢用");
                    producePosition[i] = null;
                    break;
                }
            }

            // 唤醒生产者线程进行生产
            setCondition.signalAll();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            getSemaphoe.release();

        }
    }

    public void set(){
        try {
            // 获取许可
            setSemaphore.acquire();
            lock.lock();
            while (isFull()){
                System.out.println("菜碟子满了，服务员快刷碗.....");
                setCondition.await();
            }

            for (int i = 0; i < producePosition.length ; i++) {
                if(producePosition[i] == null){
                    producePosition[i] = "A"+i;
                    System.out.println("厨师已经做好菜品"+producePosition[i]);
                    break;
                }
            }
            // 唤醒消费者线程进行消费
            getCondition.signalAll();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            setSemaphore.release();
        }


    }

    public static void main(String[] args) {
        Semaphore_02 service6 = new Semaphore_02();
        GetThread[] arrayGet = new GetThread[50];
        SetThread[] arraySet = new SetThread[50];
        for (int i=0 ; i<50 ; i++){
            arrayGet[i] = new GetThread(service6);
            arrayGet[i].setName("消费者"+ (i+1));

            arraySet[i] = new SetThread(service6);
            arraySet[i].setName("生产者"+ (i+1));
        }
        try {
            Thread.sleep(2000);
            for (int i=0 ; i<50 ; i++){
                arrayGet[i].start();
                arraySet[i].start();
            }
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
