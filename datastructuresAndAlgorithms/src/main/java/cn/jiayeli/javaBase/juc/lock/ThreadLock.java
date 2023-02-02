package cn.jiayeli.javaBase.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/1 下午3:12
 */
public class ThreadLock {

    public static void main(String[] args) {
//        LogSenCli logSenCli = new LogSenCli();
        LogSenCliV1 logSenCli = new LogSenCliV1();
        new Thread(() -> {
            while (true) {
                logSenCli.send();
            }

        }).start();

        new Thread(() -> {
            while (true) {
                logSenCli.send();
            }
        }).start();
    }

}

class LogSenCli {

    int logNum = 30;
//    public synchronized void send() {
    public void send() {
        while (logNum > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s send log-%d%n", Thread.currentThread().getName(), logNum);
            logNum--;
        }

    }

}

class LogSenCliV1 {

    int logNum = 30;
    ReentrantLock lock = new ReentrantLock();
    public void send() {
        try {
            lock.lock();
            while (logNum > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s send log-%d%n", Thread.currentThread().getName(), logNum);
                logNum--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

}
