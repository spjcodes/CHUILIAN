package cn.jiayeli.aop.JDKAOPCase.TestCase;

import cn.jiayeli.aop.JDKAOPCase.TargetClassImpl;
import cn.jiayeli.aop.JDKAOPCase.TargetInterface;
import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ApplicationStorage;
import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ExceptionHandle;
import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ExceptionHandlePersistence2ES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

public class Application {
    public static void main(String[] args) {
/*        TargetInterface targetClass = new TargetClassImpl();
        TargetInterface proxyObj = (TargetInterface) Proxy.newProxyInstance(
                targetClass.getClass().getClassLoader(),
                targetClass.getClass().getInterfaces(),
                new ExceptionHandle<TargetInterface>(targetClass)
            );

         proxyObj.nonException();

        System.out.println("------------------------------------------------\n");
        proxyObj.hasException();
        */
        String jobName = "applicationId_23242343434-424234";

        // 1. 保存ApplicationId，加在main方法里面
        ApplicationStorage.setApplicationId(jobName);
        /**
         *  Class --> methods: exception --catch--> log.error|info|waring|debug
         */

        /**
         * log1: 代理对象，需要换成具体要代理的对象
         * applicationId: 作业Id，用来标识这个日志是对于哪个作业产生的
         * log: 代理后的对象
         */


       // ------------ test -------------
       TestCase.test();

    }
}

class TestCase {

    // 2.
    static Logger log1 = LoggerFactory.getLogger(TestCase.class);
    //3.
    static Logger log = new ExceptionHandlePersistence2ES<>(log1).createProxyObj();


    public static void test() {
        log.info("log.info***********");
        log.warn("log.warn***********");
        try {
            int a = 1/0;
        } catch (Exception e) {
            log.error("log.error***********", e);
            throw new RuntimeException(e);
        }
    }
}
