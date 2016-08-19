package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.Arrays.optionalFirstOf;

import java.lang.reflect.Method;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public final class TransactionNameResolver {

    private static final String DEFAULT_PATH = "";

    private TransactionNameResolver() {
    }

    public static Optional<String> transactionNameOf(Class<?> controllerClass, Method method) {
        return requestMethodOf(method)
                .map(m -> m + " " + controllerPathOf(controllerClass))
                .map(path -> path + methodPathOf(method));
    }

    private static String methodPathOf(Method method) {
        RequestMapping annotation = methodAnnotation(method);
        return optionalFirstOf(annotation.value())
                .orElse(optionalFirstOf(annotation.path())
                        .orElse(DEFAULT_PATH));
    }

    private static String controllerPathOf(Class<?> controllerClass) {
        return controllerAnnotation(controllerClass)
                .map(RequestMapping::value)
                .flatMap(Arrays::optionalFirstOf)
                .orElse(DEFAULT_PATH);
    }

    private static Optional<String> requestMethodOf(Method method) {
        return optionalFirstOf(methodAnnotation(method).method()).map(RequestMethod::name);
    }

    private static RequestMapping methodAnnotation(Method method) {
        return method.getAnnotation(RequestMapping.class);
    }

    private static Optional<RequestMapping> controllerAnnotation(Class<?> controllerClass) {
        return Optional.ofNullable(controllerClass.getAnnotation(RequestMapping.class));
    }
}
