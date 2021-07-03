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
        Thread.currentThread().setName("threadByRunnable");
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }

    public static void main(String[] args) {
        //在该方法内调用，创建的线程就是当前的线程，故其不会新生成一个新的线程，在其他类中调用才会生成一个并行的线程
        CreateByImplementRunnable thread = new CreateByImplementRunnable();
        thread.run();

        for (int i = 10000; i > 0; i--) {

            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}
