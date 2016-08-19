package com.aconex.monitoring.prometheus.mocking;


import java.lang.reflect.Method;

public class Recorder<T> {

    private T t;
    private RecordingObject recorder;

    public Recorder(T t, RecordingObject recorder) {
        this.t = t;
        this.recorder = recorder;
    }

    public Method getCurrentMethod() {
        return recorder.getCurrentMethod();
    }

    public T getObject() {
        return t;
    }
}