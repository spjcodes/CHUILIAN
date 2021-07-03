package com.jiayeli.multiThread.threadsync.brankDemo;

/**
 * 使用Runnable接口将逻辑和线程分离
 */
public class TicketWindowV1 implements Runnable{

    private int MAX_VALUE = 0;

    @Override
    public void run() {
        //对消费逻辑加锁，一次有且仅有一个线程能执行
       synchronized (new Object()) {
           for ( ; MAX_VALUE < 50000;  ) {
               System.out.println(Thread.currentThread().getName() + ": " + MAX_VALUE++);
           }
       }
    }
}
