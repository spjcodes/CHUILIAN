package cn.jiayeli.juc.lock.consumerAndProduce;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午10:22
 */
public class LogSer<T> {

    public static void main(String[] args) {
        LogSer<String> logSer = new LogSer<>();
        LogCli<String> logCli = new LogCli<>();

        for (int i = 0; i < 800; i++) {
            new Thread(() -> {while (true) {logCli.send();}}).start();
        }

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {while (true) {logSer.process(logCli.getMsgQueue());}}).start();
        }

    }



    public synchronized void process(Queue<T> msgQueue) {
        //empty
        String name = Thread.currentThread().getName();
        while (msgQueue.isEmpty()) {
            try {
                System.out.println("msgQueue empty......");
                System.out.printf("%s_notify all\n", name);
                this.notifyAll();
                System.out.printf("%s_wait\n", name);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int size = msgQueue.size();
        System.out.printf("%s_process: %s [%d]\n", name, msgQueue.poll(), size);

    }
}

class LogCli<T> {

    private Queue<T> msgQueue = new LinkedList<>();
    private int seed = 0;
    private final int batchSize = 1000;

    public synchronized void send() {

        //full
        String name = Thread.currentThread().getName();
        while (msgQueue.size() >= batchSize) {
            try {
                System.out.println("msgQueue full!!!");
                System.out.printf("%s_notify all\n", name);
                this.notifyAll();
                System.out.printf("%s_wait\n", name);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        T logInfo = (T) String.format("log-%s", seed++);
        this.msgQueue.add(logInfo);
        System.out.printf("%s_send: %s\n", name, logInfo);

    }

    public Queue<T> getMsgQueue() {
        return msgQueue;
    }
}
