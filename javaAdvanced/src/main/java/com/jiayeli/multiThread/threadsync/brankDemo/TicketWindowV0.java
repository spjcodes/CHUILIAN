package com.jiayeli.multiThread.threadsync.brankDemo;

public class TicketWindowV0 extends Thread {

    //将数据在初始化时加载。后面的操作共享一个对象
    static int MAX_VALUE = 0;

    @Override
    public void run() {

        while(true) {
            if (MAX_VALUE > 50)
                break;
            System.out.println(Thread.currentThread().getName() + ": " + MAX_VALUE++);
        }
    }
}
