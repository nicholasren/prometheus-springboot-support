package com.aconex.monitoring.prometheus.mocking;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class RecordingObject implements MethodInterceptor {

    private Method currentMethod;

    @SuppressWarnings("unchecked")
    public static <T> Recorder<T> create(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        final RecordingObject recordingObject = new RecordingObject();

        enhancer.setCallback(recordingObject);
        return new Recorder((T) enhancer.create(), recordingObject);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] os, MethodProxy mp) throws Throwable {
        if (method.getName().equals("getCurrentMethod")) {
            return getCurrentMethod();
        } else {
            currentMethod = method;
            return DefaultValues.getDefault(method.getReturnType());
        }

    }


    public Method getCurrentMethod() {
        return currentMethod;
    }
}