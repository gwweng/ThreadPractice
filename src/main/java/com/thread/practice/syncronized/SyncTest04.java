package com.thread.practice.syncronized;

/**
 * @author gengweiweng
 * @time 2021/7/6
 * @desc
 */
public class SyncTest04 {

    // 注意事项
    // Syncronized（Object）不能使用String常量，Integer,Long
    // 原因1：JVM有String常量缓存池
    // 原因2：Integer超出缓存范围会new新对象
    // 原因3：与Integer相似
    // 锁升级：当第一个访问的线程来了，先在这个Object对象头上面的Markword记录这个线程，如果只有第一个线程访问则实际上不加锁，内部记录了这个线程的ID（偏向锁）
    // 偏向锁如果发生线程争用，就升级为自旋锁（新来的一个线程不会到CPU的就绪队列去等待，而是在这里等一下），自旋锁10次后升级为重量级锁
    // CAS不一定比系统锁效率高
    // 执行时间短，线程数少，用CAS
    // 执行时间长，线程数多，用系统锁
}
