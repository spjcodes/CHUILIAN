package cn.jiayeli.reflection;

import cn.jiayeli.aop.cglib.ExceptionHandleInterceptor;

import java.util.Arrays;


public class TestCase extends TestCaseFactory{

    public TestCase(String a1, String a2, Object a3) {
        test(a1,a2,a3);
    }
    public TestCase(String a1, String a2, Arrays a3) {
        test(a1,a2,a3);
    }



    @Override
    public void test(String a1, String a2, String a3) {
        System.out.printf("a1:%s, a2:%s, a3:%s\n", a1, a2, a3);
    }

    @Override
    public void test(String a1, String a2, Arrays a3) {
        test(a1, a2, a3);
    }
}

abstract class TestCaseFactory implements TestInterface {
    public static TestCase createTestCase(String a1, String a2, Object o) {
        return new TestCase(a1, a2, o);
    }

    public abstract void test(String a1, String a2, String a3);

    public abstract void test(String a1, String a2, Arrays a3);

    @Override
    public void test(String arg1, String arg2, Object args3) {
        System.out.println(arg1);
        System.out.println(args3.toString());
    }
}

interface TestInterface {
    public void test(String arg1, String arg2, Object args3);

}
