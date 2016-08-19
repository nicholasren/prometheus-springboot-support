package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.TransactionNameResolver.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.aconex.monitoring.prometheus.support.sample.MappingController;
import com.aconex.monitoring.prometheus.support.sample.NoMappingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class TransactionNameResolverTest {
    @DisplayName("when controller is annotated with @RequestMapping")
    @Nested
    class ForMappingController {
        private TransactionNameResolver<MappingController> resolver;

        @BeforeEach
        public void setup() {
            resolver = of(MappingController.class);
        }

        @Test
        @DisplayName("should generate transaction name for method without path")
        public void methodWithoutPath() {
            Optional<String> name = resolver.on(MappingController::without_path);
            assertThat(name).hasValue("POST /sample");
        }

        @Test
        @DisplayName("should generate name for method with path")
        public void methodWithPath() {
            Optional<String> name = resolver.on(MappingController::with_path);
            assertThat(name).hasValue("GET /sample/sub/{id}");
        }

        @Test
        @DisplayName("should generate name for url assigned via value")
        public void methodWithValue() {
            Optional<String> name = resolver.on(MappingController::with_value);
            assertThat(name).hasValue("GET /sample/sub/{id}");
        }
    }

    @DisplayName("when controller is not annotated with @RequestMapping")
    @Nested
    class ForNoMappingController {

        private TransactionNameResolver<NoMappingController> resolver;

        @BeforeEach
        public void setup() {
            resolver = of(NoMappingController.class);
        }

        @DisplayName("should generate name for method with path")
        @Test
        public void controllerWithoutPathMethodWithPath() {
            Optional<String> name = resolver.on(NoMappingController::method_with_path);
            assertThat(name).hasValue("GET /about");
        }
    }
}