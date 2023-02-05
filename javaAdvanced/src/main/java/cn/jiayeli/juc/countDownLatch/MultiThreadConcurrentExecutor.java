package cn.jiayeli.juc.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午9:52
 */
public class MultiThreadConcurrentExecutor {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s executor.\n", Thread.currentThread().getName());
        }).start();

        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s executor.\n", Thread.currentThread().getName());
        }).start();


        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s executor.\n", Thread.currentThread().getName());
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("latch count down");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s_compile", Thread.currentThread().getName());
    }
}
