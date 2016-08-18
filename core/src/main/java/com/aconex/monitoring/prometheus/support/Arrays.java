package com.aconex.monitoring.prometheus.support;

import java.util.Optional;

public class Arrays {
    static <T> Optional<T> optionalFirstOf(T[] values) {
        return values == null || values.length == 0 ? Optional.empty() : Optional.of(values[0]);
    }
}
