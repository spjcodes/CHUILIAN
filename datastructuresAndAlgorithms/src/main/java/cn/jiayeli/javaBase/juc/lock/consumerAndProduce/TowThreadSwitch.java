package cn.jiayeli.javaBase.juc.lock.consumerAndProduce;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/2 下午4:33
 */
public class TowThreadSwitch {

    public static void main(String[] args) {

        MyPrint print = new MyPrint();
        new Thread(print::printA).start();
        new Thread(print::printB).start();

    }
}

class MyPrint {

    private int flag = 0;

    public synchronized void printA() {
        while (true) {
            if (flag == 1) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flag = 1;
            System.out.println("A");
            this.notifyAll();
        }
    }

    public synchronized void printB() {
       while (true) {
           if (flag == 0) {
               try {
                   this.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           flag = 0;
           System.out.println("B");
           this.notifyAll();
       }
    }


}
