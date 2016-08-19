package com.aconex.monitoring.prometheus.support;

import java.lang.reflect.Method;

public final class Classes {
    public static Method methodRef(Class<?> controllerClass, String name) {
        Method create = null;
        try {
            create = controllerClass.getMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return create;
    }
}
