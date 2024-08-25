package cn.jiayeli.aop.exceptionHandle;

import cn.jiayeli.aop.ExceptionHandAndPersistence;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.logging.slf4j.Log4jLogger;
import org.apache.logging.slf4j.Log4jLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Test.class);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Log4jLogger.class);
        enhancer.setCallback(new LoggerProxy(Test.class.getName()));

        Logger loggerProxy = (Logger) enhancer.create(new Class[] {String.class}, new Object[] {Test.class.getName()});
        loggerProxy.info("This is a proxied log message");
    }
}

class LoggerProxy implements MethodInterceptor {

    private String loggerName;

    public LoggerProxy(String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Before invoking " + method.getName() + " method");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("After invoking " + method.getName() + " method");
        return result;
    }

}
