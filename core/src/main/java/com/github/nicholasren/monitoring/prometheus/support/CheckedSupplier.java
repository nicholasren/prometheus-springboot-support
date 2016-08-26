package com.github.nicholasren.monitoring.prometheus.support;

/**
 * Wrapper of a supplier which may throw `Throwable`
 *
 * @param <T>  type of supplied value.
 */
@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Throwable;
}
