**java高级**

#多线程
顾名思义，多线程就是指在一个进程内并行多个线程。可以充分利用资源，提升程序的性能。

##线程的创建方式

1.继承Thread类，成为一个线程
* newThread(newRunnable())
```java
//使用匿名内部类实现Runnable接口的run方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                }
            }
        }).start();
```

* extends Thread
```java
/**
 * 继承Thread类的线程创建方式
 * Thread类实现了Runnable接口，重新run方法，在run方法类实现自定义逻辑,调用start方法即可启动当前实现的线程
 */
public class CreateByExtendsThread extends Thread{
    @Override
    public void run() {
//        super.run(); 父类Runnable中定义的run方法，未实现任何逻辑
        Thread.currentThread().setName("createByThread");
        for (int i = 10000; i > 0 ; i--) {

            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }

    public static void main(String[] args) {
        CreateByExtendsThread thread = new CreateByExtendsThread();
        /**
         * 调用start方法后，会并发的开启一个线程，线程处理逻辑为调用start方法的类中run方法实现的逻辑。
         * 加之当前使用的线程，此时会有两个并行的线程
         */
        thread.start();

        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }

        /**
         * 最后的执行结果CreateByExtendsThread.run方法和main方法的for循环会交替执行，
         * 即两个线程并行执行，但是在同一时刻，其仅其会有一个线程得到cpu的时间分片，所以会不规则的进行交替打印
         */
    }
}
```
3.实现Runnable接口，成为一个可执行的任务
>使用Runnable接口可以将线程与逻辑分开，更为灵活
* implements Runnable
```java
/**
 * 实现Runnable接口，该接口是一个只提供了run方法的普通接口,其实列将会有线程来执行。具体代码如下
 *<p>
 * @FunctionalInterface
 *  public interface Runnable {
 *  ／／当实现接口的对象Runnable被用来创建一个线程，启动线程使对象的run在独立执行的线程中调用的方法。
 *     public abstract void run();
 *  }
 *</p>
 */
public class CreateByImplementRunnable implements Runnable{

    @Override
    public void run() {
        Thread.currentThread().setName("threadByRunnable");
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }

    public static void main(String[] args) {
        //在该方法内调用，创建的线程就是当前的线程，故其不会新生成一个新的线程，在其他类中调用才会生成一个并行的线程
        CreateByImplementRunnable thread = new CreateByImplementRunnable();
        thread.run();

        for (int i = 10000; i > 0; i--) {

            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}
```
##Thread和Runnable的区别
* Runnable就是一个可执行任务的标识而已，仅此而已，而Thread才是线程所有API的体现；
继承了Thread父类就没有办法去继承其他类，而实现了Runnable接口也可以继承其他类并且实现其他接口，这个区别也是很多书中千篇一律提到的，其实Java中的对象即
使继承了其他类，也可以通过再构造一个父类的方式继承很多个类，或者通过内部类的方式继承很多个类，因此这个区别个人觉得不痛不痒；
* 将任务执行单元和线程的执行控制区分开来，这才是引入Runnable最主要的目的，
Thread你就是一个线程的操作者，或者独裁者，你有Thread的所有方法，而Runnable只是一个任务的标识，只有实现了它才能称之为一个任务，这也符合面向对象接口的逻
辑，接口其实就是行为的规范和标识；
  
## 线程同步


#IO


#反射


#设计模式