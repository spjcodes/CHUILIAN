package com.jiayeli.multiThread.create;

/**
 * 继承Thread类的线程创建方式
 * Thread类实现了Runnable接口，重新run方法，在run方法类实现自定义逻辑,调用start方法即可启动当前实现的线程
 */
public class CreateByExtendsThread extends Thread{
    @Override
    public void run() {
//        super.run(); 父类Runnable中定义的run方法，未实现任何逻辑
        Thread.currentThread().setName("createByThread");
        for (int i = 10000; i > 0 ; i--) {

            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }

    public static void main(String[] args) {
        CreateByExtendsThread thread = new CreateByExtendsThread();
        /**
         * 调用start方法后，会并发的开启一个线程，线程处理逻辑为调用start方法的类中run方法实现的逻辑。
         * 加之当前使用的线程，此时会有两个并行的线程
         */
        thread.start();

        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }

        /**
         * 最后的执行结果CreateByExtendsThread.run方法和main方法的for循环会交替执行，
         * 即两个线程并行执行，但是在同一时刻，其仅其会有一个线程得到cpu的时间分片，所以会不规则的进行交替打印
         */



    }
}
