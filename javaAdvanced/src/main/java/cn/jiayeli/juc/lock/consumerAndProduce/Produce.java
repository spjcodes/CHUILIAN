package cn.jiayeli.juc.lock.consumerAndProduce;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午5:28
 */
public class Produce<T> {

    private Queue<T> msgQueue = new LinkedList<T>();
    private int size = 30;
    private int seed = 0;
    public void produce() {

        synchronized (msgQueue) {
            //full
            while (msgQueue.size() > size) {
                try {
                    msgQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T record = (T) String.format("record_-%d", seed++);
            this.msgQueue.add(record);
            System.out.printf("%s_add: %s\n", Thread.currentThread().getName(), record);
            msgQueue.notifyAll();
        }


    }

    public Queue<T> getMsgQueue() {
        return msgQueue;
    }

}
