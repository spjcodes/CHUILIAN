package com.jiayeli.multiThread.threadsync.brankDemo;

/**
 * 并行执行，但是数据会重复消费
 */
public class Brank_v0 {

    public static void main(String[] args) {

         final int MAX_VALUE = 0;


        TicketWindowV0 t1 = new TicketWindowV0();
        TicketWindowV0 t2 = new TicketWindowV0();
        TicketWindowV0 t3 = new TicketWindowV0();
        t1.start();
        t2.start();
        t3.start();


        /**
         * 同时有三个线程执行相同的逻辑，数据会被重复消费,
         * 因为每个线程都有一个MAX_VALUE，会都执行MAX_VALUE次.
         * 可以将MAX_VALUE作为静态值。这样子数据就只有一份，由三个线程共享
         * 打印结果：
         * hread-0: 0
         * Thread-0: 1
         * Thread-0: 2
         * Thread-0: 3
         * Thread-1: 0
         * Thread-0: 4
         * Thread-1: 1
         * Thread-1: 2
         * Thread-1: 3
         * Thread-0: 5
         * Thread-1: 4
         * Thread-0: 6
         * Thread-1: 5
         * Thread-1: 6
         * Thread-1: 7
         * Thread-1: 8
         * Thread-1: 9
         * Thread-0: 7
         * Thread-1: 10
         * Thread-0: 8
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
         * Thread-1: 25
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
         * Thread-1: 43
         * Thread-1: 44
         * Thread-1: 45
         * Thread-1: 46
         * Thread-1: 47
         * Thread-1: 48
         * Thread-1: 49
         * Thread-1: 50
         * Thread-0: 9
         * Thread-0: 10
         * Thread-0: 11
         * Thread-0: 12
         * Thread-0: 13
         * Thread-0: 14
         * Thread-0: 15
         * Thread-0: 16
         * Thread-0: 17
         * Thread-0: 18
         * Thread-0: 19
         * Thread-0: 20
         * Thread-0: 21
         * Thread-0: 22
         * Thread-0: 23
         * Thread-0: 24
         * Thread-0: 25
         * Thread-0: 26
         * Thread-0: 27
         * Thread-0: 28
         * Thread-0: 29
         * Thread-0: 30
         * Thread-0: 31
         * Thread-0: 32
         * Thread-0: 33
         * Thread-0: 34
         * Thread-0: 35
         * Thread-0: 36
         * Thread-0: 37
         * Thread-0: 38
         * Thread-0: 39
         * Thread-0: 40
         * Thread-0: 41
         * Thread-0: 42
         * Thread-0: 43
         * Thread-0: 44
         * Thread-0: 45
         * Thread-0: 46
         * Thread-0: 47
         * Thread-0: 48
         * Thread-0: 49
         * Thread-0: 50
         * Thread-2: 0
         * Thread-2: 1
         * Thread-2: 2
         * Thread-2: 3
         * Thread-2: 4
         * Thread-2: 5
         * Thread-2: 6
         * Thread-2: 7
         * Thread-2: 8
         * Thread-2: 9
         * Thread-2: 10
         * Thread-2: 11
         * Thread-2: 12
         * Thread-2: 13
         * Thread-2: 14
         * Thread-2: 15
         * Thread-2: 16
         * Thread-2: 17
         * Thread-2: 18
         * Thread-2: 19
         * Thread-2: 20
         * Thread-2: 21
         * Thread-2: 22
         * Thread-2: 23
         * Thread-2: 24
         * Thread-2: 25
         * Thread-2: 26
         * Thread-2: 27
         * Thread-2: 28
         * Thread-2: 29
         * Thread-2: 30
         * Thread-2: 31
         * Thread-2: 32
         * Thread-2: 33
         * Thread-2: 34
         * Thread-2: 35
         * Thread-2: 36
         * Thread-2: 37
         * Thread-2: 38
         * Thread-2: 39
         * Thread-2: 40
         * Thread-2: 41
         * Thread-2: 42
         * Thread-2: 43
         * Thread-2: 44
         * Thread-2: 45
         * Thread-2: 46
         * Thread-2: 47
         * Thread-2: 48
         * Thread-2: 49
         * Thread-2: 50
         */

        /**
         *
         */

    }
}
