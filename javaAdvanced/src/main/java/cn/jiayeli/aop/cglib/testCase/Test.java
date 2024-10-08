package cn.jiayeli.aop.cglib.testCase;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.TypeVariable;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
            int primitiveInt = 10;
            Integer integerObject = new Integer(10);

            boolean matchedInt = matchTypes(primitiveInt, integerObject);
            System.out.println("Matched Integer: " + matchedInt);

            double primitiveDouble = 10.0;
            Double doubleObject = new Double(10.0);

            boolean matchedDouble = matchTypes(primitiveDouble, doubleObject);
            System.out.println("Matched Double: " + matchedDouble);

        long primitiveLong = 10L;
        Long longObject = Long.valueOf(10);
        boolean matchedLong = matchTypes(primitiveLong, longObject);
        System.out.println("Matched Long: " + matchedDouble);

        float f = 10.00f;
        Float F = 10.00f;
        boolean matchedFloat = matchTypes(F, f);
        System.out.println("Matched Long: " + matchedDouble);
        }

        public static <T, U> boolean matchTypes(T primitive, U wrapper) {
            if (primitive.getClass().getName().equals(getWrapperClassName(wrapper))) {
                return true;
            }
            return false;
        }

        public static <U> String getWrapperClassName(U wrapper) {
            return wrapper.getClass().getName();
        }
//        noArgsConstruct();
//    }

    private static void noArgsConstruct() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetClass.class);
        enhancer.setCallback(new CustomInterceptor());

        TargetClass proxy = (TargetClass) enhancer.create();
        proxy.parentMethod();
        proxy.targetMethod("arg1", 123);
    }

    public static void withArgsConstruct() {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(TargetClass.class);
    MethodInterceptor callback = new MethodInterceptor() {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("Before method: " + method.getName());
            Object result = proxy.invokeSuper(obj, args);
            System.out.println("After method: " + method.getName());
            return result;
        }
    };
    enhancer.setCallback(callback);

    // 使用带参数的构造函数创建代理对象
    TargetClass proxy = (TargetClass) enhancer.create(
            new Class[]{String.class, int.class},
            new Object[]{"example", 42}
    );

    // 调用代理对象的方法
    proxy.targetMethod("test", 123);
}
}





class ParentClass {
    public void parentMethod() {
        System.out.println("Parent method called");
    }
}

class TargetClass extends ParentClass {
 /*   public TargetClass(String arg1, int arg2) {
        System.out.println("Target class constructor called with arguments: " + arg1 + ", " + arg2);
    }*/
    public void targetMethod(String arg1, int arg2) {
        System.out.println("Target method called with arguments: " + arg1 + ", " + arg2);
    }
}

class CustomInterceptor implements MethodInterceptor {
    /**
     *
     * @param obj "this", the enhanced object
     * @param method intercepted Method
     * @param args argument array; primitive types are wrapped
     * @param proxy used to invoke super (non-intercepted method); may be called as many times as needed
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Before executing method: " + method.getName() + " with arguments: " + Arrays.toString(args));
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("After executing method: " + method.getName());
        return result;
    }
}
