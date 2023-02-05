package cn.jiayeli.multiThread.create;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author: jiayeli.cn
 * @description
 * <p>
 *     Multiple indicators are computed in parallel, and the result process is blocked*
 * </p>
 * @date: 2023/2/5 下午6:52
 */
public class CreateByCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int subTakNum = 10;
        ArrayList<String> dataSet = new ArrayList<>();
        HashMap<String, Future<Integer>> futureTaskCollect = new HashMap<>();
        Map<String, Integer> resultCollect = Collections.synchronizedMap(new HashMap<>());

        /*for (int i = 0; i < subTakNum; i++) {
            String target = String.format("target_%d", i);
            FutureTask<Integer> futureTask = new FutureTask<>(new Task<String, Integer>(target));
            new Thread(futureTask).start();
            Integer result = futureTask.get();
            resultCollect.put(target, result);
            System.out.printf("[%s]: %s complete.\n", Thread.currentThread().getName(), target);
        }*/

        for (int i = 0; i < subTakNum; i++) {
            String data = String.format("target_%d", i);
            dataSet.add(data);
        }

       dataSet.forEach(data -> {
           FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task<String, Integer>(data));
           futureTaskCollect.put(data, futureTask);
           new Thread(futureTask).start();
       });

        for (Map.Entry<String, Future<Integer>> futureTask : futureTaskCollect.entrySet()) {
            Integer result = futureTask.getValue().get();
            String accTarget = futureTask.getKey();
            System.out.printf("[%s]: process task-%s result\n", Thread.currentThread().getName(), accTarget);
            resultCollect.put(accTarget, result);
        }

        System.out.printf("[%s]: process result--------------\n", Thread.currentThread().getName());
        resultCollect.entrySet().forEach(System.out::println);
        System.out.printf("[%s]: main thread complete..\n", Thread.currentThread().getName());
    }
}

class Task<T, R> implements Callable<R> {

    private final T data;

    Task(T data) {
        this.data =data;
    }

    @Override
    public R call() throws Exception {
        System.out.printf("[%s]: process data-%s\n", Thread.currentThread().getName(), data);
        String tempResult = String.valueOf(this.data).substring("target_".length());
        R result = (R) Integer.valueOf(tempResult);
        TimeUnit.SECONDS.sleep((Integer) result % 5 == 0 ? 6 : 1);
        return result;
    }
}


