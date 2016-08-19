package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.Classes.method;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Optional;

import com.aconex.monitoring.prometheus.support.sample.MappingController;
import com.aconex.monitoring.prometheus.support.sample.NoMappingController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class TransactionNameResolverTest {
    private TransactionNameResolver resolver = TransactionNameResolver.of();

    @DisplayName("when controller is annotated with @RequestMapping")
    @Nested
    class ForMappingController {

        @Test
        @DisplayName("should generate transaction name for method without path")
        public void methodWithoutPath() {
            Method without_path = method(MappingController.class, "without_path");
            Optional<String> name = resolver.on(MappingController.class, without_path);

            assertThat(name).hasValue("POST /sample");
        }

        @Test
        @DisplayName("should generate name for method with path")
        public void methodWithPath() {
            Method with_path = method(MappingController.class, "with_path");
            Optional<String> name = resolver.on(MappingController.class, with_path);

            assertThat(name).hasValue("GET /sample/sub/{id}");
        }

        @Test
        @DisplayName("should generate name for url assigned via value")
        public void methodWithValue() {
            Method with_value = method(MappingController.class, "with_path");
            Optional<String> name = resolver.on(MappingController.class, with_value);

            assertThat(name).hasValue("GET /sample/sub/{id}");
        }
    }

    @DisplayName("when controller is not annotated with @RequestMapping")
    @Nested
    class ForNoMappingController {

        @DisplayName("should generate name for method with path")
        @Test
        public void controllerWithoutPathMethodWithPath() {
            Method with_path = method(NoMappingController.class, "with_path");
            Optional<String> name = resolver.on(NoMappingController.class, with_path);
            assertThat(name).hasValue("GET /about");
        }
    }
}