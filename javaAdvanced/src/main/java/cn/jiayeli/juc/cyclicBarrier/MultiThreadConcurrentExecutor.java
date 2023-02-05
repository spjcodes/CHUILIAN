package cn.jiayeli.juc.cyclicBarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiayeli.cn
 * @description demo Wait for all child threads to complete the ResultProcess function calculation and finish
 *This can be done using countDownLatch:
 * * countdownLatch decreases by 1 and await on the main thread after the child thread completes execution*
 * @date: 2023/2/5 下午5:04
 */
public class MultiThreadConcurrentExecutor<T> {

    public static void main(String[] args) {

        int threadNum = 3;

        List syncArrList = Collections.synchronizedList(new ArrayList());

        // callBack
        class  ResultProcess implements Runnable {
            @Override
            public void run() {
                System.out.printf("[%s]_through barrier, process result...\n", Thread.currentThread().getName());
                int result = syncArrList.stream().mapToInt(e -> (int) e).sum();
                System.out.printf("result: %d", result);
            }
        }

        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum, new ResultProcess());
        TaskProcesser logSer = new TaskProcesser(cyclicBarrier, syncArrList);

        for (int i = 5; i < 5+threadNum; i++) {
            Task<String, TaskProcesser> task = new Task<>(String.format("task_%d", i), logSer);
            new Thread(task).start();
        }

        System.out.printf("[%s]: main thread complete\n", Thread.currentThread().getName());

    }

}

class Task<T,E> implements Runnable {

    T data;
    E task;

    Task(T data, E task) {
        this.data = data;
        this.task = task;
    }

    @Override
    public void run() {
        TaskProcesser res = (TaskProcesser) task;
        res.process(data);
    }
}



class TaskProcesser<T> {

    private CyclicBarrier cyclicBarrier;
    private List syncArrList;

    public TaskProcesser(CyclicBarrier cyclicBarrier, List syncArrList) {
        this.cyclicBarrier = cyclicBarrier;
        this.syncArrList = syncArrList;
    }

    public void process(T task) {
        try {
            Integer result = Integer.valueOf(
                    ((String) task).substring("task_".length()));
            //process task
            TimeUnit.SECONDS.sleep(result%6 == 0 ?  6:1);
            System.out.printf("[%s]_process task:%s\n", Thread.currentThread().getName(), (T) task);
            this.syncArrList.add(result);
            //wait cyclicBarrier
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
