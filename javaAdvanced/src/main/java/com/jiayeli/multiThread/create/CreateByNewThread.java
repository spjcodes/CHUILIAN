package com.jiayeli.multiThread.create;

public class CreateByNewThread {

    public static void main(String[] args) {

        //使用匿名内部类实现Runnable接口的run方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                }
            }
        }).start();

        for (int i = 100; i > 0; i--) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }

    }
}
