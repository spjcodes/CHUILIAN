import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午6:15
 */
public class PCTestCase {

    /**
     * 队列长度
     */
    private static final int MAX_LEN = 5;

    /**
     * 队列
     */
    private Queue<Integer> queue = new LinkedList<>();

    class Producer extends Thread {

        @Override
        public void run() {
            producer();
        }

        /**
         * 生产者
         */
        private void producer() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == MAX_LEN) { //队列已满
                        queue.notify();
                        System.out.println("当前队列已满");
                        try {
                            queue.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    queue.add(1);
                    queue.notify();
                    System.out.println("生产者生成了一条数据，当前队列的长度为：" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            consumer();
        }

        private void consumer() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == 0) {
                        queue.notify();
                        System.out.println("当前队列为空");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    queue.notify();
                    System.out.println("消费者消费一条任务，当前队列的长度为：" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        PCTestCase consumerAndProducer = new PCTestCase();
        Producer producer = consumerAndProducer.new Producer();
        Consumer consumer = consumerAndProducer.new Consumer();
        producer.start();
        consumer.start();
    }

}
