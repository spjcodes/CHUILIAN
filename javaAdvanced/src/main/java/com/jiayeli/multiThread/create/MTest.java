package com.jiayeli.multiThread.create;

/**
 * 其最终结果三个线程会交替执行，
 * 但是CreateByImplementsRunnable类只实现了普通的Runnable接口，为什么会单独成了一个线程呢？？？
 * 迷惑迷惑迷惑 ƪ(˘⌣˘)ʃ
 * 还有我实现的run方法，为什么可以调用start方法来启动线程呢？？？
 * 迷惑迷惑迷惑 ƪ(˘⌣˘)ʃ
 */
public class MTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<1000; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                }
            }
        }).start();

        new CreateByExtendsThread().start();

/**
 * 使用模板模式，将处理逻辑与线程分开。
 * 实现Runnable接口即可成为一个可执行的任务。其被标记为可执行任务，然后又jvm创建线程并执行
 */
//new CreateByImplementRunnable().run();
        Thread thread = new Thread(new CreateByImplementRunnable());
        thread.start();
    }
}
