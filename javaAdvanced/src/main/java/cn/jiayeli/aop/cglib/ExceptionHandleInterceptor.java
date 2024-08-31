package cn.jiayeli.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ExceptionHandleInterceptor<T> implements MethodInterceptor {

    public T createProxyObj(Class<T> targetClazz, Object... args) throws NoSuchMethodException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClazz);
        MethodInterceptor callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("Before method: " + method.getName());
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("After method: " + method.getName());
                return result;
            }
        };
//        enhancer.setCallback(callback);
        enhancer.setCallback(this);
        // 使用带参数的构造函数创建代理对象
        return (T) enhancer.create(
                findConstructor(targetClazz, args).getParameterTypes(),
                args
        );
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before method: " + method.getName());
        Object result = methodProxy.invokeSuper(obj, objects);
        System.out.println("After method: " + method.getName());
        return result;
    }

    public static   Constructor<?> findConstructor(Class<?> clazz, Object... args) {
        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> c : constructors) {
            if (c.getParameterCount() == Arrays.stream(args).count()) {
                Class<?>[] parameterTypes = c.getParameterTypes();
                boolean matches = true;
                for (int i = 0; i < args.length; i++) {
                    if (!parameterTypes[i].isInstance(args[i])) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    return c;
                }
            }
        }
        return null;
    }
}

