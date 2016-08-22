package com.aconex.monitoring.prometheus.collector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;

public class TransactionLatencyCollectorTest {

    @Mock
    private TransactionNameResolver resolver;

    private TransactionLatencyCollector collector;

    @DisplayName("should resolve transaction name")
    public void shouldResolveTransactionName() {

    }

    @Nested
    @DisplayName("When transcation name not found")
    class NoMeasureScenarios {

        @DisplayName("should not measure")
        public void donotMeasure() {

        }
    }

    @Nested
    @DisplayName("When transcation name is found")
    class MeasureScenarios {
        @DisplayName("should measure")
        public void donotMeasure() {

        }
    }
}