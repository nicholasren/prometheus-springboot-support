package com.github.nicholasren.monitoring.prometheus.collector;

import java.lang.reflect.Method;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

class TransactionNameResolver {

    private static final String DEFAULT_PATH = "";

    public static TransactionNameResolver create() {
        return new TransactionNameResolver();
    }

    public Optional<String> nameOf(Class<?> cls, Method method) {
        return httpMethodOn(method)
                .map(httpMethod -> httpMethod + " " + mappingPathOf(cls))
                .map(path -> path + mappingPathOf(method));
    }

    private Optional<String> httpMethodOn(Method method) {
        return mappingOf(method)
                .map(RequestMapping::method)
                .flatMap(Arrays::first)
                .map(RequestMethod::name);
    }

    private String mappingPathOf(Class<?> cls) {
        return pathOf(mappingOf(cls));
    }

    private String mappingPathOf(Method method) {
        return pathOf(mappingOf(method));
    }

    private String pathOf(Optional<RequestMapping> annotation) {
        return annotation
                .flatMap(a -> Arrays.first(a.value(), a.path()))
                .orElse(DEFAULT_PATH);
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
