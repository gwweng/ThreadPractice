package com.thread.practice.dcl;

/**
 * 单例模式-饿汉式
 * 类加载到内存后，被实例化一个单例，JVM保证线程安全
 * 优点：简单实用
 * 唯一缺点：不管用到与否，类加载时就完成实例化
 * @author gengweiweng
 * @time 2021/7/6
 * @desc
 *
 */
public class Mgr01 {
    private static final Mgr01 INSTANCE = new Mgr01();
    // 单例模式关键：构造方法私有
    private Mgr01(){

    }
    public static Mgr01 getInstance(){
        return INSTANCE;
    }

    public void print(){
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        Mgr01.getInstance().print();
    }


}
