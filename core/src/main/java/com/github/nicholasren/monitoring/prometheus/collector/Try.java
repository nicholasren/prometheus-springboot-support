package com.github.nicholasren.monitoring.prometheus.collector;

class Try {
    static <T> T of(CheckedSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {//NOPMD
            throw new RuntimeException(t);
        }
    }
}
