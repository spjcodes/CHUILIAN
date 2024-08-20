package cn.jiayeli.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;


public class ExceptionHandAndPersistence implements MethodInterceptor {

    private Class<?> targetClass;
    public targetClass createProxyObj(Class<?> targetClas) {
        this.targetClass = targetClas;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClas);
        enhancer.setCallback(this);
        targetClass t =  (targetClass) enhancer.create();
        return t;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy){
        Object result = null;
        try {
            result =  methodProxy.invokeSuper(o, objects);
        } catch (Throwable e) {
            persistenceExceptionInfo(method, objects, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private void persistenceExceptionInfo(Method method, Object[] objects, Throwable e) {
        System.out.println("ExceptionHandAndPersistence");
        System.out.println("object:" + this.targetClass.getName());
        System.out.println("methods:" + method.getName());
        System.out.println("parameters:" + Arrays.toString(objects));
        System.out.println("exceptionInfo:" + e.getMessage());
        System.out.println("exceptionCauseInfo:" + Optional.ofNullable(e.getCause()).orElse(new RuntimeException("Empty")).toString());
    }
}
