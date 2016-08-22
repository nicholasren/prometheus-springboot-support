package com.aconex.monitoring.prometheus.collector;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

class Aspects {
    static Class<?> targetClassOf(ProceedingJoinPoint pjp) {
        return pjp.getTarget().getClass();
    }

    public static Method targetMethodOf(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }
}
