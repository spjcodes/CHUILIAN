package cn.jiayeli.juc.lock.consumerAndProduce;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午5:37
 */
public class TestCase {

    public static void main(String[] args) {
        Produce<String> produce = new Produce<>();
        Consumer<String> consumer = new Consumer<String>(produce.getMsgQueue());


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    produce.produce();
                }
            }, "produce"+i).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    consumer.consumer();
                }
            }, "consumer" + i).start();
        }
    }
}
