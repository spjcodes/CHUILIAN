package com.jiayeli.multiThread.threadsync;

class Dead {

    private Object lock = new Object();// 自定义的一个锁
    private int x = 0;

    public void methodA() {
        synchronized (lock)// 先用lock锁住程序
        {
            synchronized (this) {
                System.out.println("method a .." + (x++));
            }
        }
    }

    public void methodB() {
        synchronized (this) {
            synchronized (lock) {
                System.out.println("method b .." + (x++));
            }
        }
    }
}

public class DadeLock {
    public static void main(String[] args) {

        final Dead dead = new Dead();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    dead.methodA();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    dead.methodB();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}