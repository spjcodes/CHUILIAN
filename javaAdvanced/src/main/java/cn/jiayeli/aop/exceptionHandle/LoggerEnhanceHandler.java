package cn.jiayeli.aop.exceptionHandle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class LoggerEnhanceHandler<T> implements InvocationHandler {

    public T createProxyObj(Object targetClass, Object[] args) {
        Class<?> superclass = targetClass.getClass().getSuperclass();
        Object proxyObj = Proxy.newProxyInstance(
//                classLoader
               targetClass.getClass().getClassLoader(),
//                interfaces
                new Class[]{TargetClassTest.class},
//                invocationHandle
                new LoggerEnhanceHandler<T>()
        );
        return (T) proxyObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            result = method.invoke(args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.printf("enhanceLoggerProxy process exception: %s%n", e.toString());
            throw new RuntimeException(e);
        }
        return result;
    }
}
