package com.github.nicholasren.monitoring.prometheus.collector;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Optional;

import com.github.nicholasren.monitoring.prometheus.utils.Classes;
import com.github.nicholasren.monitoring.prometheus.utils.Controllers;
import com.github.nicholasren.monitoring.prometheus.utils.CustomizedException;
import io.prometheus.client.Histogram;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class TransactionLatencyCollectorTest {

    private TransactionNameResolver resolver = mock(TransactionNameResolver.class);

    private ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class, RETURNS_DEEP_STUBS);

    private Histogram histogram = mock(Histogram.class, RETURNS_DEEP_STUBS);

    private MethodSignature methodSignature = mock(MethodSignature.class);

    private final Histogram.Timer timer = mock(Histogram.Timer.class);

    private Method method = Classes.method(Controllers.NoMappingController.class, "with_path");

    private Class cls = Controllers.NoMappingController.class;

    private TransactionLatencyCollector collector = new TransactionLatencyCollector(histogram, resolver);

    @Nested
    @DisplayName("When transaction name not found")
    class NoMeasureScenarios {

        @BeforeEach
        public void setup() {
            setupPjp();
            when(resolver.nameOf(cls, method)).thenReturn(Optional.empty());
        }

        @DisplayName("should not measure")
        @Test
        public void doNotMeasure() throws Throwable {
            collector.measure(pjp);
            verifyZeroInteractions(histogram);
        }
    }

    @Nested
    @DisplayName("When transaction name is found")
    class MeasureScenarios {
        private String transactionName = "GET /sample";

        @BeforeEach
        public void setup() {
            setupPjp();
            when(resolver.nameOf(any(), any())).thenReturn(Optional.of(transactionName));
            when(histogram.labels(anyString()).startTimer()).thenReturn(timer);
        }

        @DisplayName("should measure")
        @Test
        public void shouldMeasure() throws Throwable {
            collector.measure(pjp);
            verify(histogram.labels(transactionName)).startTimer();
            verify(timer).observeDuration();
        }

        @Nested
        @DisplayName("when exception occurs")
        class WhenExceptionOccurs {

            private CustomizedException customizedException;

            @BeforeEach
            public void setup() throws Throwable {
                customizedException = new CustomizedException("error happened");

                when(pjp.proceed()).thenThrow(customizedException);
            }

            @DisplayName("should retain existing exception")
            @Test
            public void shouldRetainExistingException() {
                CustomizedException exception = assertThrows(CustomizedException.class, () -> {
                    collector.measure(pjp);
                });
                assertThat(exception.getMessage()).isEqualTo(customizedException.getMessage());
            }
        }
    }

    private void setupPjp() {
        when(pjp.getTarget().getClass()).thenReturn(cls);
        when(pjp.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
    }
}