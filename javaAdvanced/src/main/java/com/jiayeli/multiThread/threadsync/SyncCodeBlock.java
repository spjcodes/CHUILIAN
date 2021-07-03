package com.jiayeli.multiThread.threadsync;

/**
 * 当多个线程对同一个资源进行操作的时候，可以对操作的资源进行加锁，从而保证在加锁的代码块内同一时刻只会有一个线程对资源进行访问。
 * 同步代码块的语法如下：
 * synchronized (new Object()) {
 *
 * }
 *
 *
 */
public class SyncCodeBlock {

    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow();

        Thread t1 = new Thread(ticketWindow);
        Thread t2 = new Thread(ticketWindow);
        Thread t3 = new Thread(ticketWindow);
        Thread t4 = new Thread(ticketWindow);
        Thread t5 = new Thread(ticketWindow);
        Thread t6 = new Thread(ticketWindow);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }

}

class TicketWindow implements Runnable{
    protected int MAX_USER_NUM = 0;

    public synchronized void getMoney() {
        while(true) {
            //设置一个延时时间，将在方法内的时间延长，使效果更明显
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             *给判断的代码加锁，一次只能有一个线程进行执行加锁的代码块。
             * 如果不加的话MAX_USER_NUM的最终值可能会大于100000。如下：
             * 有两个线程，前一个MAX_USER_NUM=99999，后一个MAX_USER_NUM=99998
             * 在同一时间段内，两个线程都过了if（）判断，
             * 此时前一个的MAX_USER_NUM=99999++ 变成了100000，
             * 但是后一个线程已经过了判断了，所以后一个线程最终值就是100001
             */
            //synchronized (new Object()) {
                if(MAX_USER_NUM > 100) {
                    System.out.println("=================================" + Thread.currentThread().getName() +  "今日业务已办理完，请明天再来=================================");
                    break;
                }

                System.out.println(Thread.currentThread().getName() + ": " + MAX_USER_NUM++);
            //}
        }
    }

    @Override
    public void run() {
      //  this.getMoney();
        while(true) {
            //设置一个延时时间，将在方法内的时间延长，使效果更明显
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             *给判断的代码加锁，一次只能有一个线程进行执行加锁的代码块。
             * 如果不加的话MAX_USER_NUM的最终值可能会大于100000。如下：
             * 有两个线程，前一个MAX_USER_NUM=99999，后一个MAX_USER_NUM=99998
             * 在同一时间段内，两个线程都过了if（）判断，
             * 此时前一个的MAX_USER_NUM=99999++ 变成了100000，
             * 但是后一个线程已经过了判断了，所以后一个线程最终值就是100001
             */
            synchronized (new Object()) {
                if(MAX_USER_NUM > 100) {
                    System.out.println("=================================" + Thread.currentThread().getName() +  "今日业务已办理完，请明天再来=================================");
                    break;
                }

                System.out.println(Thread.currentThread().getName() + ": " + MAX_USER_NUM++);
            }
        }
    }
}
