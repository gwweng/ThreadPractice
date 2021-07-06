package com.thread.practice.dcl;

/**
 * 懒汉式-双重检锁
 * @author gengweiweng
 * @time 2021/7/6
 * @desc
 */
public class Mgr02 {
    // 双重检锁要加volatile，防止指令重排序
    // 1.给指令申请内存
    // 2.给成员变量初始化
    // 3.内存的内容赋值给INSTANCE
    // 指令重排可能会导致123的顺序错乱，当另外的线程进来时就会发现值已经有了，而会把初始化不完全的对象返回出去导致错误
    private static volatile Mgr02 INSTANCE;

    private Mgr02(){

    }

    public  static Mgr02 getInstance(){
        if(INSTANCE == null){
            synchronized (Mgr02.class) {
                System.out.println("当前线程id：" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(INSTANCE == null){
                    INSTANCE = new Mgr02();
                }
            }
        }
        return INSTANCE;
    }

    public void print(){
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        for (int i=0 ; i<10 ; i++){
            new Thread(() -> System.out.println(Mgr02.getInstance().hashCode())).start();
        }
    }
}
