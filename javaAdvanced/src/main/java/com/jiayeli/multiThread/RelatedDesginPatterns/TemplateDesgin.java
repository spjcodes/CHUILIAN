package com.jiayeli.multiThread.RelatedDesginPatterns;

/**
 * template模式，
 * 模板模式，将具体的算法逻辑和控制逻辑交给父类来定义，将功能模块封装，交由子类去实现具体逻辑
 */
abstract class Diagram {
    //隐藏具体细节，让子类只关注功能模块的实现即可
    protected char c ;

    public Diagram(char c) {
        this.c = c;
    }

    abstract protected void print(int size);

    abstract protected void printContent(String msg);

    /**
     * 具体的处理逻辑由父类实现，之类只需要实现相关模块的逻辑代码即可
     * @param msg
     */
    public final void display(String msg) {
        int len = msg.getBytes().length;
        print(len);
        printContent(msg);
        print(len);
    }
}

/**
 * 之类继承父类，实现具模块的处理代码，不需要关心父类隐藏的具体上层处理逻辑
 */
class StarDiagram extends Diagram {

    public StarDiagram(char c) {
        super(c);
    }

    /**
     * 实现父类提供的功能模块
     * @param size
     */
    @Override
    protected void print(int size) {
        for(int i = 0;i<size+2;i++) {
            System.out.print(c);
        }
        System.out.println();
    }

    /**
     * 实现父类提供的功能模块处理逻辑
     * @param msg
     */
    @Override
    protected void printContent(String msg) {
        System.out.print("*");
        System.out.print(msg);
        System.out.println("*");
    }
}

public class TemplateDesgin {

    public static void main(String[] args) {
        //父类方法指向子类，java多态。
        Diagram d1 = new StarDiagram('*');
        //调用父类的处理逻辑，具体的功能模块由子模块来实现
        d1.display("啊吧啊吧");
    }
}