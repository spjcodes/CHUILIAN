package com.jiayeli.multiThread.productAndConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Test
 * @ProjectName: chuilian
 * Description: test
 * @Author: SHIPENGJUN
 * @Date: 2021/7/11 14:55
 * @Version: v0.1
 * @change logs:
 * user: SHIPENGJUN
 * modified info: 初始创建
 */
public class ProductConsumer_v2 {

    //共享资源，其会有多个线程来并行操作
    private static volatile List<String> containerObject = new ArrayList();


    public static void main(String[] args) {

        Producer product = new Producer(containerObject);
        Consumer consumer1 = new Consumer(containerObject);
        Consumer consumer2 = new Consumer(containerObject);
        Consumer consumer3 = new Consumer(containerObject);

        product.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }


    static class Consumer extends Thread {
        private List<String> container;
        
        public Consumer(List container) {
            this.container = container;
        }
        @Override
        public void run() {
            try {

                for (; ; ) {

                    while (container.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " list为空");
                        System.out.println(Thread.currentThread().getName() + " 稍等1秒钟");
                        Thread.sleep(3000);
                    }

                    //可能会有多个线程同时操作同一个元素,
                    // 存在同一时刻多个线程删除的是同一个位置的元素,
                    // 或有两个线程同时在上面的判断为false，然后一个线程抢先remove了index为0位置的元素,别的线程就会报 IndexOutOfBoundsException
                    System.out.println(Thread.currentThread().getName() + "消费了 :" + container.remove(0));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Producer extends Thread {
        private List<String> container;

        public Producer(List container) {
            this.container = container;
        }

        @Override
        public void run() {
            int i = 0;
            int t = i;
            System.out.println(Thread.currentThread().getName() + " 开始添加元素");
            for (; ; ) {

                t = i++;
                container.add(t + "");
                System.out.println(Thread.currentThread().getName() + " 添加元素" + t);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
