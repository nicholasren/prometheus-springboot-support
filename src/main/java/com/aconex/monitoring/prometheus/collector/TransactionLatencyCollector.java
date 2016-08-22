package com.aconex.monitoring.prometheus.collector;

import com.aconex.monitoring.prometheus.support.CheckedSupplier;
import com.aconex.monitoring.prometheus.support.Try;
import io.prometheus.client.Histogram;

public final class TransactionLatencyCollector {
    private static final Histogram REQUEST_LATENCY = Histogram.build()
            .name("controller_latency_seconds")
            .labelNames("transaction")
            .help("Controller latency in seconds")
            .register();

    public static <T> T measure(String transactionName, CheckedSupplier<T> supplier) {
        Histogram.Timer timer = REQUEST_LATENCY.labels(transactionName).startTimer();
        try {
            return Try.of(supplier);
        } finally {
            timer.observeDuration();
        }
    }

    private TransactionLatencyCollector() {

    }
}