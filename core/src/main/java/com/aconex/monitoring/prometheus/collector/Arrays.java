package com.aconex.monitoring.prometheus.collector;

import java.util.Optional;
import java.util.stream.Stream;

class Arrays {

    static <T> Optional<T> first(T[]... ts) {
        return Stream.of(ts)
                .filter(t -> t.length > 0)
                .findFirst()
                .map(t -> t[0]);
    }
}
