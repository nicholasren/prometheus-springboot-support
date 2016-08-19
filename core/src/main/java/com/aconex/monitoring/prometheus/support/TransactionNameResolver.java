package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.Arrays.first;

import java.lang.reflect.Method;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public final class TransactionNameResolver {

    private static final String DEFAULT_PATH = "";

    public static TransactionNameResolver create() {
        return new TransactionNameResolver();
    }

    public Optional<String> nameOf(Class<?> clazz, Method method) {
        return httpMethodOn(method)
                .map(httpMethod -> httpMethod + " " + mappingPathOf(clazz))
                .map(path -> path + mappingPathOf(method));
    }

    private String mappingPathOf(Method method) {
        return pathOf(mappingOf(method));
    }

    private String mappingPathOf(Class<?> cls) {
        return pathOf(mappingOf(cls));
    }

    private String pathOf(Optional<RequestMapping> annotation) {
        return annotation
                .flatMap(a -> first(a.value(), a.path()))
                .orElse(DEFAULT_PATH);
    }

    private Optional<String> httpMethodOn(Method method) {
        return first(annotationOf(method).method()).map(RequestMethod::name);
    }

    private RequestMapping annotationOf(Method method) {
        return method.getAnnotation(RequestMapping.class);
    }

    private Optional<RequestMapping> mappingOf(Method method) {
        return Optional.ofNullable(method.getAnnotation(RequestMapping.class));
    }

    private Optional<RequestMapping> mappingOf(Class<?> cls) {
        return Optional.ofNullable(cls.getAnnotation(RequestMapping.class));
    }

    private TransactionNameResolver() {
    }
}
