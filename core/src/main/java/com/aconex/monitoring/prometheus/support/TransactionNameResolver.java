package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.Arrays.firstOf;
import static com.aconex.monitoring.prometheus.support.Arrays.or;

import java.lang.reflect.Method;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public final class TransactionNameResolver {

    private static final String DEFAULT_PATH = "";

    private TransactionNameResolver() {
    }

    public Optional<String> on(Class<?> cls, Method m) {
        return httpMethodOf(m)
                .map(httpMethod -> httpMethod + " " + mappingPathOf(cls))
                .map(path -> path + mappingPathOf(m));
    }

    private static String mappingPathOf(Method method) {
        return path(mappingOf(method));
    }

    private static String mappingPathOf(Class<?> cls) {
        return path(mappingOf(cls));
    }

    private static String path(Optional<RequestMapping> annotation) {
        return annotation
                .flatMap(a -> or(firstOf(a.value()), firstOf(a.path())))
                .orElse(DEFAULT_PATH);
    }

    private static Optional<String> httpMethodOf(Method method) {
        return firstOf(annotationOf(method).method()).map(RequestMethod::name);
    }

    private static RequestMapping annotationOf(Method method) {
        return method.getAnnotation(RequestMapping.class);
    }

    private static Optional<RequestMapping> mappingOf(Method method) {
        return Optional.ofNullable(method.getAnnotation(RequestMapping.class));
    }

    private static Optional<RequestMapping> mappingOf(Class<?> cls) {
        return Optional.ofNullable(cls.getAnnotation(RequestMapping.class));
    }

    public static TransactionNameResolver of() {
        return new TransactionNameResolver();
    }
}
