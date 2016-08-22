package com.github.nicholasren.monitoring.prometheus.collector;

import static com.github.nicholasren.monitoring.prometheus.collector.Aspects.targetClassOf;
import static com.github.nicholasren.monitoring.prometheus.collector.Aspects.targetMethodOf;

import java.util.Optional;

import io.prometheus.client.Histogram;
import org.aspectj.lang.ProceedingJoinPoint;

public final class TransactionLatencyCollector {

    private final Histogram metric;

    private final TransactionNameResolver resolver;

    public static TransactionLatencyCollector create() {
        return new TransactionLatencyCollector(metric(), TransactionNameResolver.create());
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
        Histogram.Timer timer = metric.labels(transactionName).startTimer();
        try {
            return Try.of(proceed);
        } finally {
            timer.observeDuration();
        }
    }

    TransactionLatencyCollector(Histogram metric, TransactionNameResolver resolver) {
        this.metric = metric;
        this.resolver = resolver;
    }

    private static Histogram metric() {
        return Histogram.build()
                .name("controller_latency_seconds")
                .labelNames("transaction")
                .help("Controller latency in seconds")
                .register();
    }
}