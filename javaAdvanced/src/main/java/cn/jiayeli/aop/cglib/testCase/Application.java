package cn.jiayeli.aop.cglib.testCase;

import cn.jiayeli.aop.cglib.ExceptionHandleInterceptor;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        TestCase testCase = TestCaseFactory
                .createTestCase("a1", "a2", new Object());

        TestCase testCastProxy = null;
        try {
            testCastProxy = new ExceptionHandleInterceptor<TestCase>()
                    .createProxyObj(TestCase.class, "a1", "a2", new Object());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        testCastProxy.test("testArgs1Str", "testArgs2String", Arrays.asList("a", "b", "b"));

    }
}

class TestCase extends TestCaseFactory{

    public TestCase(String a1, String a2, Object a3) {
        test(a1,a2,a3);
    }



    @Override
    public void test(String a1, String a2, String a3) {
        System.out.printf("a1:%s, a2:%s, a3:%s\n", a1, a2, a3);
    }
}

abstract class TestCaseFactory implements TestInterface {
    public static TestCase createTestCase(String a1, String a2, Object o) {
        return new TestCase(a1, a2, o);
    }

    public abstract void test(String a1, String a2, String a3);

    @Override
    public void test(String arg1, String arg2, Object args3) {
        System.out.println(arg1);
        System.out.println(args3.toString());
    }
}

interface TestInterface {
    public void test(String arg1, String arg2, Object args3);

}
