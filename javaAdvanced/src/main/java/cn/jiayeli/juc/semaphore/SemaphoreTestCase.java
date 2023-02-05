package cn.jiayeli.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/5 下午9:00
 */
public class SemaphoreTestCase {

    public static void main(String[] args) {
        int max_thread_num = 100;
        int max_run_num = 5;
        Semaphore semaphore = new Semaphore(max_run_num);
        for (int i = 0; i < max_thread_num; i++) {
            new Task(semaphore).start();

        }

    }
}

class Task extends Thread {
    private final Semaphore semaphore;

    Task(Semaphore semaphore) {
        this.semaphore =semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.printf("[%s]: acquire permit" +
                    "...\n", Thread.currentThread().getName());
            semaphore.acquire();
            System.out.printf("[%s]: start process...\n", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("[%s]: process complete release permit...\n", Thread.currentThread().getName());
            semaphore.release();
        }
    }
}
