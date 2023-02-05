package cn.jiayeli.juc.lock.consumerAndProduce;

import java.util.Queue;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午5:26
 */
public class Consumer<T> {
    private Queue<T> msgQueue;

    public Consumer(Queue<T> msgQueue) {
        this.msgQueue = msgQueue;
    }

    public void consumer() {
        synchronized (msgQueue) {
            //empty
            while (this.msgQueue.isEmpty()) {
                try {
                    System.out.printf("%s_msgQueueEmpty wait...\n", Thread.currentThread().getName());
                    System.out.printf("%s_notifyAll\n", Thread.currentThread().getName());
                    msgQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("%s_consumer: %s\n", Thread.currentThread().getName(), this.msgQueue.poll());
            msgQueue.notifyAll();
        }

    }
}
