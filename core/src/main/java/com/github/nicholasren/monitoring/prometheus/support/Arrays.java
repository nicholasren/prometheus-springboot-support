package com.github.nicholasren.monitoring.prometheus.support;

import java.util.Optional;
import java.util.stream.Stream;

public class Arrays {

    @SafeVarargs
    public static <T> Optional<T> first(T[]... ts) {
        return Stream.of(ts)
                .filter(t -> t.length > 0)
                .findFirst()
                .map(t -> t[0]);
    }
}
