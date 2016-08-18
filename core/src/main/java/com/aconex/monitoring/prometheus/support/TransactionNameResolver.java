package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.Arrays.optionalFirstOf;

import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public final class TransactionNameResolver {

    private static final String DEFAULT_PATH = "";

    private TransactionNameResolver() {
    }

    public static Optional<String> transactionNameOf(ProceedingJoinPoint pjp) {
        return requestMethodOf(pjp)
                .map(method -> method + " " + controllerPathOf(pjp))
                .map(path -> path + methodPathOf(pjp));
    }

    private static String methodPathOf(ProceedingJoinPoint pjp) {
        RequestMapping annotation = methodAnnotation(pjp);
        return optionalFirstOf(annotation.value())
                .orElse(optionalFirstOf(annotation.path())
                        .orElse(DEFAULT_PATH));
    }

    private static String controllerPathOf(ProceedingJoinPoint pjp) {
        return controllerAnnotation(pjp)
                .map(RequestMapping::value)
                .flatMap(Arrays::optionalFirstOf)
                .orElse(DEFAULT_PATH);
    }

    private static Optional<String> requestMethodOf(ProceedingJoinPoint pjp) {
        return optionalFirstOf(methodAnnotation(pjp).method()).map(RequestMethod::name);
    }

    private static RequestMapping methodAnnotation(ProceedingJoinPoint pjp) {
        return ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(RequestMapping.class);
    }

    private static Optional<RequestMapping> controllerAnnotation(ProceedingJoinPoint pjp) {
        return Optional.ofNullable(pjp.getTarget().getClass().getAnnotation(RequestMapping.class));
    }
}
