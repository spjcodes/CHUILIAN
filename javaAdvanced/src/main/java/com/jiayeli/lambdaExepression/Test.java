package com.jiayeli.lambdaExepression;

public class Test {

    public static void main(String[] args) {

        //使用匿名内部类创建一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello Thread by Anonymous inner class");
            }
        }).start();

        //使用lambda表达式实现一个匿名内部类
        new Thread(() -> System.out.println("hello lambda expression")).start();

        TestMethod m = (name) ->  {return   String.valueOf(name); };

        new Test().invokingTest("kuro", "coding", m);

    }

    private void invokingTest(String name, String hoby, TestMethod method) {
        System.out.println(method.apply(name, hoby));
    }





}
