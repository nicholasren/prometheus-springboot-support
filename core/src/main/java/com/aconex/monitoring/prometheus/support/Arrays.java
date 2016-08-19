package com.aconex.monitoring.prometheus.support;

import java.util.Optional;
import java.util.stream.Stream;

public class Arrays {
    static <T> Optional<T> firstOf(T[] values) {
        return values == null || values.length == 0 ? Optional.empty() : Optional.of(values[0]);
    }

    static <T> Optional<T> or(Optional<T>... ts) {
        return Stream.of(ts)
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }
}
