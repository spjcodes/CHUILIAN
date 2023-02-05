package cn.jiayeli.multiThread.productAndConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @ClassName: ProductConsumer
 * @ProjectName: chuilian
 * Description: 多线程生产者消费者问题
 * @Author: SHIPENGJUN
 * @Date: 2021/7/11 22:59
 * @Version: v0.1
 * @change logs:
 * user: SHIPENGJUN
 * modified info: 初始创建
 */
public class ProductConsumer {

    public static void main(String[] args) {

        Cache cache = new Cache();
        Product product = new Product(cache);
        Consumer consumer = new Consumer(cache);

        Thread product1 = new Thread(product);
        product1.setName("product1");
        Thread product2 = new Thread(product);
        product2.setName("product2");


        Thread consumer1 = new Thread(consumer);
        consumer1.setName("consumer1");
        Thread consumer2 = new Thread(consumer);
        consumer1.setName("consumer2");
        Thread consumer3 = new Thread(consumer);
        consumer1.setName("consumer3");

        product1.start();
        product2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();

    }
}

class Cache {
    int maxCapacity = 20;
    private Queue<Integer> buffer;

    public Cache() {
        this.buffer = new LinkedList<Integer>();
    }

    public int takeData() throws InterruptedException {

        //缓存中没数据，等待生产者生产
        while (buffer.size() < 1) {
            System.out.println("dear " + Thread.currentThread().getName() + " 缓存没数据了，等我会儿");
//            System.out.println("dear " + Thread.currentThread().getName() + " 缓存没数据了，好了叫你");
            Thread.sleep(Math.abs(new Random().nextInt(1000)));
        }
        Integer data = buffer.remove();
        System.out.println(Thread.currentThread().getName() + "消费了数据" + data);
        return data;
    }


    public boolean saveDate() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            while (buffer.size() > maxCapacity - 1) {
                System.out.println("缓存满了，抽根烟在搞生产吧");
                Thread.sleep(Math.abs(new Random().nextInt(1000)));
                return false;
            }
            this.buffer.add(Integer.valueOf(i));
            System.out.println(Thread.currentThread().getName() + "缓存了数据: " + new Integer(i));

        }
        return true;
    }
}

class Consumer implements Runnable {

    private Cache cache = new Cache();

    public Consumer(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                 cache.takeData();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Product implements Runnable {

    private Cache cache;

    public Product(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                System.out.println(Thread.currentThread().getName() + "生产了数据" + cache.saveDate());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
