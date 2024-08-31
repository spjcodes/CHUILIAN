package cn.jiayeli.aop.cglib.testCase;

import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ApplicationStorage;
import cn.jiayeli.aop.cglib.interceptors.ExceptionHandleInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Application {

    static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args){
        testCase01();
    }

    public static void testCase01()  {


//        1.
        ApplicationStorage.setApplicationId(String.format("test-application-%05d", System.currentTimeMillis()));

//      2.
        // TestCase testCase = new TestCase("a", "b", 11, "22", "33", new Test(), 1);

        TestCase testCase = null;
        try {
            testCase = new ExceptionHandleInterceptor<TestCase>().createProxyObj(TestCase.class, "a", "b", 11, "22", "33", new Test(), 1);
        } catch (NoSuchMethodException e) {

            throw new RuntimeException(e);
        }

// 业务逻辑
        testCase.hasException();
    }


    private static void logTest() {
        log.debug("info message");
        log.info("info message");
        log.warn("info message");
        log.error("info message");
    }

    private static void ExceptionHandleWithArgsTestCase() {
        ApplicationStorage.setApplicationId(String.format("test-application-%05d", System.currentTimeMillis()));

        TestCase testCase = new TestCase("a1", "a2", new Object());
        TestCase testCastProxy = null;
        try {
            testCastProxy = new ExceptionHandleInterceptor<TestCase>()
//                   TestCase.class： 需要进行代理的类，代理对象的参数
                    .createProxyObj(TestCase.class, "a1", "a2", new Object());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

//        testCastProxy.test("testArgs1Str", "testArgs2String", Arrays.asList("a", "b", "b"));
//        testCastProxy.hasException();
//        调用代理对象的方法
        testCastProxy.hasException(1,"exceptionParameter", Arrays.asList("a", "b", "b"), new Test());
    }



    private static void ExceptionHandleNoArgsTestCase() {
//     1.   设置jobName到内存中,后面写es时候会拿到这个jobName做参数
        ApplicationStorage.setApplicationId(String.format("test-application-%05d", System.currentTimeMillis()));
// ====================== 需要该的地方 ================================
//        TestCase t = new TestCase();
//    2.   testCastProxy：代理后的对象，改成具体要代理的类
        TestCase t = null;
//  =================================================================
//  ------------------------------------------- 需要添加 --------------------------------------
        try {
//    3.       TestCase：需要代理的对象,改成具体要代理的类, 如果代理对象有参数，把参数直接添加到后面
            t = new ExceptionHandleInterceptor<TestCase>()
                    .createProxyObj(TestCase.class);
        } catch (NoSuchMethodException e) {
            log.error("create proxy obj error", e);
            throw new RuntimeException(e);
        }
// -------------------------------------------------- END --------------------------------------
//        testCastProxy.test("testArgs1Str", "testArgs2String", Arrays.asList("a", "b", "b"));
//        testCastProxy.hasException();
//      4. 使用代理对象
        t.hasException(1,"exceptionParameter", Arrays.asList("a", "b", "b"), new Test());
    }
}



class TestCase extends TestCaseFactory{

    public TestCase(String a1, String a2, Object a3) {
        test(a1,a2,a3);
    }

    public TestCase() {
        test();
    }

    public TestCase(String a, String b, int i, String number, String number1, Test test, int o) {
        System.out.println("*******");
    }


    @Override
    public void test(String a1, String a2, String a3) {
        System.out.printf("a1:%s, a2:%s, a3:%s\n", a1, a2, a3);
    }

    @Override
    public void test() {
        System.out.println("test ..........");
    }

    @Override
    public <T> void hasException(int i, String exceptionParameter, List<T> list, Test test) {
        System.out.println("parametrs: " + i + ", " + exceptionParameter + ", " + list + ", " + test);
        int e = 1/0;
    }
}

abstract class TestCaseFactory implements TestInterface {
    public static TestCase createTestCase(String a1, String a2, Object o) {
        return new TestCase(a1, a2, o);
    }

    public abstract void test(String a1, String a2, String a3);

    public abstract void test();

    @Override
    public void test(String arg1, String arg2, Object args3) {
        System.out.printf("arg1:%s, arg2:%s, args3:%s\n", arg1, arg2, args3);
    }

    @Override
    public void hasException() {
        System.out.println("invoke hasException method..");
        Arrays.asList("A", "b", "C").forEach(e -> System.out.println(e));
        int e = 1/0;
    }

    public abstract <T> void hasException(int i, String exceptionParameter, List<T> list, Test test);
}

interface TestInterface {
    void test(String arg1, String arg2, Object args3);
    void hasException() ;
}
