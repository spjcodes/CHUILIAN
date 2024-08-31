package cn.jiayeli.reflection;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class FindConstructorUtils <T>{

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

    public static   Constructor<?> findConstructor(Class<?> clazz, Object... args) {
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