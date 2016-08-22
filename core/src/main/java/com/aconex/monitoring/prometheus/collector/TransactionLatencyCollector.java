package com.aconex.monitoring.prometheus.collector;

import static com.aconex.monitoring.prometheus.collector.Aspects.targetClassOf;
import static com.aconex.monitoring.prometheus.collector.Aspects.targetMethodOf;

import java.util.Optional;

import io.prometheus.client.Histogram;
import org.aspectj.lang.ProceedingJoinPoint;

public final class TransactionLatencyCollector {

    private static final Histogram REQUEST_LATENCY = Histogram.build()
            .name("controller_latency_seconds")
            .labelNames("transaction")
            .help("Controller latency in seconds")
            .register();

    private TransactionNameResolver resolver;

    public static TransactionLatencyCollector create() {
        return new TransactionLatencyCollector(TransactionNameResolver.create());
    }

    public Object measure(ProceedingJoinPoint pjp) {
        Optional<String> name = resolver.nameOf(targetClassOf(pjp), targetMethodOf(pjp));
        if (name.isPresent()) {
            return measure(name.get(), pjp::proceed);
        } else {
            return Try.of(pjp::proceed);
        }
    }

    private Object measure(String transactionName, CheckedSupplier<Object> proceed) {
        Histogram.Timer timer = REQUEST_LATENCY.labels(transactionName).startTimer();
        try {
            return Try.of(proceed);
        } finally {
            timer.observeDuration();
        }
    }

    TransactionLatencyCollector(TransactionNameResolver resolver) {
        this.resolver = resolver;
    }
}