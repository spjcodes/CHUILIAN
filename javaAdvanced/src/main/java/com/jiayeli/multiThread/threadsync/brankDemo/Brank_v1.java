package com.jiayeli.multiThread.threadsync.brankDemo;

public class Brank_v1 {

    public static void main(String[] args) {
        /**
         * 起了三个线程共同调用同一个逻辑（对象），
         * 三个线程会分别进行数据处理，不会重复处理同一个数据
         */
        TicketWindowV1 ticketWindows = new TicketWindowV1();
        Thread t1 = new Thread(ticketWindows);
        Thread t2 = new Thread(ticketWindows);
        Thread t3 = new Thread(ticketWindows);
        t1.start();
        t2.start();
        t3.start();

        /**
         * 打印结果如下：
         * Thread-0: 0
         * Thread-0: 1
         * Thread-1: 2
         * Thread-1: 4
         * Thread-1: 5
         * Thread-1: 6
         * Thread-1: 7
         * Thread-1: 8
         * Thread-1: 9
         * Thread-1: 10
         * Thread-1: 11
         * Thread-1: 12
         * Thread-1: 13
         * Thread-1: 14
         * Thread-1: 15
         * Thread-1: 16
         * Thread-1: 17
         * Thread-1: 18
         * Thread-1: 19
         * Thread-1: 20
         * Thread-1: 21
         * Thread-1: 22
         * Thread-1: 23
         * Thread-1: 24
         * Thread-1: 26
         * Thread-1: 27
         * Thread-1: 28
         * Thread-1: 29
         * Thread-1: 30
         * Thread-1: 31
         * Thread-1: 32
         * Thread-1: 33
         * Thread-1: 34
         * Thread-1: 35
         * Thread-1: 36
         * Thread-1: 37
         * Thread-1: 38
         * Thread-1: 39
         * Thread-1: 40
         * Thread-1: 41
         * Thread-1: 42
         * Thread-0: 3
         * Thread-0: 44
         * Thread-0: 45
         * Thread-0: 46
         * Thread-0: 47
         * Thread-0: 48
         * Thread-0: 49
         * Thread-1: 43
         * Thread-2: 25
         */


    }
}
