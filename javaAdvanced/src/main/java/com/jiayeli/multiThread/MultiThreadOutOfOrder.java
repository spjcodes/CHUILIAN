package com.jiayeli.multiThread;

public class MultiThreadOutOfOrder {
    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("A");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("B");
                }
            }
        }).start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("C");
        }
    }
}
