package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.Arrays.optionalFirstOf;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.aconex.monitoring.prometheus.mocking.Recorder;
import com.aconex.monitoring.prometheus.mocking.RecordingObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public final class TransactionNameResolver<Controller> {

    private static final String DEFAULT_PATH = "";
    private final Class<Controller> cls;
    private final Recorder<Controller> recorder;

    private TransactionNameResolver(Class<Controller> cls) {
        this.cls = cls;
        this.recorder = RecordingObject.create(cls);
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

    public static <Controller> TransactionNameResolver<Controller> of(Class<Controller> clazz) {
        return new TransactionNameResolver<>(clazz);
    }

    public <U> Optional<String> on(Function<Controller, U> action) {
        action.apply(recorder.getObject());
        Method method = recorder.getCurrentMethod();
        return transactionNameOf(cls, method);
    }

    public Optional<String> on(Consumer<Controller> action) {
        action.accept(recorder.getObject());
        Method method = recorder.getCurrentMethod();
        return transactionNameOf(cls, method);
    }
}
