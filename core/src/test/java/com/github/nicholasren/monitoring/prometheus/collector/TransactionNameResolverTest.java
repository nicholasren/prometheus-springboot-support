package com.github.nicholasren.monitoring.prometheus.collector;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Optional;

import com.github.nicholasren.monitoring.prometheus.utils.Controllers.MappingController;
import com.github.nicholasren.monitoring.prometheus.utils.Controllers.NoMappingController;
import com.github.nicholasren.monitoring.prometheus.utils.Classes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class TransactionNameResolverTest {
    private TransactionNameResolver resolver = TransactionNameResolver.create();

    @DisplayName("when controller is annotated with @RequestMapping")
    @Nested
    class ForMappingController {

        @Test
        @DisplayName("should generate transaction name for method without path")
        public void methodWithoutPath() {
            Method without_path = Classes.method(MappingController.class, "without_path");
            Optional<String> name = resolver.nameOf(MappingController.class, without_path);

            assertThat(name).hasValue("POST /sample");
        }

        @Test
        @DisplayName("should generate name for method with path")
        public void methodWithPath() {
            Method with_path = Classes.method(MappingController.class, "with_path");
            Optional<String> name = resolver.nameOf(MappingController.class, with_path);

            assertThat(name).hasValue("GET /sample/with_path/{id}");
        }

        @Test
        @DisplayName("should generate name for url assigned via value")
        public void methodWithValue() {
            Method with_value = Classes.method(MappingController.class, "with_value");
            Optional<String> name = resolver.nameOf(MappingController.class, with_value);

            assertThat(name).hasValue("GET /sample/with_value/{id}");
        }

        @Test
        @DisplayName("should not generate name for no HTTP Method")
        public void methodWithoutHttpMethod() {
            Method with_value = Classes.method(MappingController.class, "without_http_method");
            Optional<String> name = resolver.nameOf(MappingController.class, with_value);
            assertThat(name).isNotPresent();
        }
    }

    @DisplayName("when controller is not annotated with @RequestMapping")
    @Nested
    class ForNoMappingController {

        @DisplayName("should generate name for method with path")
        @Test
        public void controllerWithoutPathMethodWithPath() {
            Method with_path = Classes.method(NoMappingController.class, "with_path");
            Optional<String> name = resolver.nameOf(NoMappingController.class, with_path);
            assertThat(name).hasValue("GET /with_path");
        }

        @DisplayName("should generate name for method with value")
        @Test
        public void controllerWithoutPathMethodWithValue() {
            Method with_value = Classes.method(NoMappingController.class, "with_value");
            Optional<String> name = resolver.nameOf(NoMappingController.class, with_value);
            assertThat(name).hasValue("GET /with_value");
        }
    }
}