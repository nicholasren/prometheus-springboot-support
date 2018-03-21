package com.github.nicholasren.monitoring.prometheus.support;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public final class Aspects {
    public static Class<?> targetClassOf(ProceedingJoinPoint pjp) {
        return pjp.getTarget().getClass();
    }

    public static Method targetMethodOf(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

    private Aspects() {

    }
}
