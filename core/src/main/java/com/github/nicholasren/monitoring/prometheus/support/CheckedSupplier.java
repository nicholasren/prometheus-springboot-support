package com.github.nicholasren.monitoring.prometheus.support;

/**
 * Wrapper of a supplier which may throw `Throwable`
 *
 * @param <T>
 */
@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Throwable;
}
