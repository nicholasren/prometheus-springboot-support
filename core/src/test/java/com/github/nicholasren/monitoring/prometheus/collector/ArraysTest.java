package com.github.nicholasren.monitoring.prometheus.collector;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.nicholasren.monitoring.prometheus.support.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class ArraysTest {

    @Test
    public void shouldReturnTheOnlyValue() {
        Assertions.assertThat(Arrays.first(of(1))).hasValue(1);
    }

    @Test
    public void shouldReturnThePresentValue() {
        assertThat(Arrays.first(empty(), of(1))).hasValue(1);
    }

    @Test
    public void shouldReturnTheFirstPresentValue() {
        assertThat(Arrays.first(empty(), of(2), of(1))).hasValue(2);
    }

    private Integer[] of(int i) {
        return new Integer[]{i};
    }

    private Integer[] empty() {
        return new Integer[]{};
    }
}