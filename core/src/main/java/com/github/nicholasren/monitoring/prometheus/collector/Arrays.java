package com.github.nicholasren.monitoring.prometheus.collector;

import java.util.Optional;
import java.util.stream.Stream;

class Arrays {

    @SafeVarargs
    static <T> Optional<T> first(T[]... ts) {
        return Stream.of(ts)
                .filter(t -> t.length > 0)
                .findFirst()
                .map(t -> t[0]);
    }
}
