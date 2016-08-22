package com.aconex.monitoring.prometheus.collector;

/**
 * Wrapper of a supplier which may throw `Throwable`
 *
 * @param <T>
 */
@FunctionalInterface
interface CheckedSupplier<T> {
    T get() throws Throwable;
}
