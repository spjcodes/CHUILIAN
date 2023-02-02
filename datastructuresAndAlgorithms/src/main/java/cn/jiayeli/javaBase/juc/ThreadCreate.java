package cn.jiayeli.javaBase.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/1 下午1:58
 */
public class ThreadCreate {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread.start();

        MyThread myThread = new MyThread();
        // print result "main" because this is a general method
        myThread.run();
        myThread.start();

        FutureTask<String> futureTask = new FutureTask<String>(new MyCallableTask());
        Thread thread1 = new Thread(futureTask);
        try {
            System.out.println(Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread1.start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }

   static class MyThread extends Thread {
       @Override
       public void run() {
           try {
               TimeUnit.SECONDS.sleep(3);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println(Thread.currentThread().getName());
       }
   }

   static class MyCallableTask implements Callable<String> {
       @Override
       public String call() throws Exception {
           TimeUnit.SECONDS.sleep(2);
           System.out.println(Thread.currentThread().getName());
           if (true) {
               throw new RuntimeException("MyCallableTask exception");
           }
           return "result";
       }
   }

    public void doublyStartCommonThread() {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });

        thread.start();
        //Exception in thread "main" java.lang.IllegalThreadStateException
        //	at java.lang.Thread.start(Thread.java:705)
        /**
         *  if (threadStatus != 0)
         *             throw new IllegalThreadStateException();*
         */
        thread.start();

    }
}
