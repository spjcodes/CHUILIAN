package com.jiayeli.multiThread.create;

/**
 * 实现Runnable接口，该接口是一个只提供了run方法的接口,具体代码如下
 *<p>
 * @FunctionalInterface
 *  public interface Runnable {
 *     public abstract void run();
 *  }
 *</p>
 */
public class CreateByImplementRunnable implements Runnable{

    @Override
    public void run() {
        //Thread.currentThread().setName("threadByRunnable");
        for (int i = 0; i < 10000; i++) {
            System.out.println("int run()" + Thread.currentThread().getName() + ": " + i);
        }
    }

    public static void main(String[] args) {

        //打印当前线程名
        System.out.printf("创建main方法的线程为:%s",Thread.currentThread().getName());
        System.out.println("..........................................................");

        CreateByImplementRunnable thread = new CreateByImplementRunnable();
        //main方法中普通java对象的方法调用，使用的是main线程，并不会创建新的线程
        thread.run();
        //创建一个新的线程
        new Thread(thread).start();

    }
}
