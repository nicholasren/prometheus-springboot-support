package com.aconex.monitoring.prometheus.support;

public class Try {

    public static <T> T of(CheckedSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {//NOPMD
            throw new RuntimeException(t);
        }
    }
}
