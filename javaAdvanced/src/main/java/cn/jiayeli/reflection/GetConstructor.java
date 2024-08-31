package cn.jiayeli.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class GetConstructor {

    public static void main(String[] args) {
        Object[] testArgs = {"a1", "a2", Arrays.asList("a", "b", "c")};
        TestCase object = new TestCase("a1", "a2", Arrays.asList("a", "b", "c"));
//        getConstructorInfo(object);
        Utils<TestCase> testCaseUtils = new Utils<>();
        testCaseUtils.getInstance(object, "a1", "a2", Arrays.asList("a", "b", "c"));
        Utils<T> ttestCaseUtils = new Utils<>();
        T t = ttestCaseUtils.getInstance(new T(), "Kiran");
        t.hello();

    }



    private static void getConstructorInfo(Object object) {
        try {
            Class<?> clazz = Class.forName(object.getClass().getName());

            // 获取所有公共构造方法
            Constructor<?>[] constructors = clazz.getConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println("Constructor: " + constructor.getName());

                // 获取构造方法的参数
                Parameter[] parameters = constructor.getParameters();


                for (Parameter parameter : parameters) {
                    System.out.println("Parameter: " + parameter.getName() + " - " + parameter.getType() + " - " + parameter.isNamePresent());
                    Method method = clazz.getMethod(String.format("get%s", Character.toUpperCase(parameter.getName().charAt(0)) + parameter.getName().substring(1)), parameter.getType());
                    System.out.println("method: " + method.getName());


                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

class T {

    public void hello() {
        System.out.println("hello :) ");
    }
}

class Utils<T>{
    public T getInstance(T object, Object... args) {
        T t = null;
        try {
            Class<?> clazz = Class.forName(object.getClass().getName());

            Constructor<?> constructor = findConstructor(clazz, args);
            if (Optional.ofNullable(constructor).isPresent() && args.length > 0) {
                t = (T) constructor.newInstance(args);
            } else {
                t = (T) clazz.newInstance();
            }

            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    private static Constructor<?> findConstructor(Class<?> clazz, Object... args) {
        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> c : constructors) {
            if (c.getParameterCount() == args.length) {
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