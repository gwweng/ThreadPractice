package com.thread.practice.juc.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Phaser（阶段）是CountDownLatch和CyclicBarrier的结合
 * 模拟：结婚现场
 * @author gengweiweng
 * @time 2021/7/12
 * @desc
 */
public class Phaser_01 {
    static Random random = new Random();
    static MarriagePhaser marriagePhaser = new MarriagePhaser();

    public static void main(String[] args) {

        marriagePhaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p"+i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }
    static class  MarriagePhaser extends Phaser{
        /**
         * 所有线程满足栅栏条件onAdvance会被触发，阶段是被写死从0开始的
         * @param phase 第几个阶段
         * @param registeredParties 目前这阶段有几个人参加
         * @return 返回true所有线程结束
         */
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase){
                case 0:
                    System.out.println("所有人到齐了!" + registeredParties);
                    return false;
                case 1:
                    System.out.println("所有人都吃饭完了！"+ registeredParties);
                case 2:
                    System.out.println("所有人都离开了！"+ registeredParties);
                    return false;
                case 3:
                    System.out.println("婚礼结束，新郎新娘拥抱！"+ registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person implements Runnable{
        String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }

        public void millsleep(int mill){
            try {
                TimeUnit.MILLISECONDS.sleep(mill);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void arrive(){
            millsleep(random.nextInt(1000));
            System.out.printf("%s 到达现场！\n",name);
            // 当前线程在栅栏面前停住
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void eat(){
            millsleep(random.nextInt(1000));
            System.out.printf("%s 吃完！\n",name);
            marriagePhaser.arriveAndAwaitAdvance();
        }
        public void leave(){
            millsleep(random.nextInt(1000));
            System.out.printf("%s 离开！\n",name);
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void hug(){
            if(name.equals("新郎") || name.equals("新娘")){
                millsleep(random.nextInt(1000));
                System.out.printf("%s 洞房！\n",name);
                marriagePhaser.arriveAndAwaitAdvance();
            }else {
                //marriagePhaser.register(); 往上加栅栏个数和栅栏上的等待数量
                marriagePhaser.arriveAndDeregister();//不参与
            }
        }
    }
}
