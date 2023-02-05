package cn.jiayeli.juc.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午9:38
 */
public class WaitOtherThreadCompile {

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 2;
        CountDownLatch latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new WorkThread(latch).start();
        }

        //block wait countDownLatch value to 0 executor next logic
        latch.await();
        System.out.println("main Thread compile..");
    }
}

class WorkThread extends Thread {

    private final CountDownLatch latch;

    public WorkThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s_compile\n", Thread.currentThread().getName());
        latch.countDown();
    }

}
