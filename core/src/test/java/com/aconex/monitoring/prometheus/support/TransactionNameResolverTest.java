package com.aconex.monitoring.prometheus.support;

import static com.aconex.monitoring.prometheus.support.Classes.methodRef;
import static com.aconex.monitoring.prometheus.support.TransactionNameResolver.transactionNameOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

public class TransactionNameResolverTest {
    @DisplayName("when controller has mapping")
    @Nested
    class ForMappingController {

        @Test
        @DisplayName("should generate transaction name for method without path")
        public void methodWithoutPath() {
            assertThat(transactionNameFor(MappingController.class, "method_without_path")).isEqualTo("POST /sample");
        }

        @Test
        @DisplayName("should generate name for method with path")
        public void methodWithPath() {
            assertThat(transactionNameFor(MappingController.class, "method_with_path")).isEqualTo("GET /sample/sub/{id}");
        }

        @Test
        @DisplayName("should generate name for url assigned via value")
        public void methodWithValue() {
            assertThat(transactionNameFor(MappingController.class, "method_with_value")).isEqualTo("GET /sample/sub/{id}");
        }
    }


    @Nested
    class ForNoMappingController {
        @DisplayName("should generate name for method with path")
        @Test
        public void controllerWithoutPathMethodWithPath() {
            assertThat(transactionNameFor(NoMappingController.class, "method_with_path")).isEqualTo("GET /about");
        }
    }


    @RestController
    @RequestMapping("/sample")
    private static class MappingController {
        @RequestMapping(method = RequestMethod.POST)
        public void method_without_path() {
        }

        @RequestMapping(method = RequestMethod.GET, path = "/sub/{id}")
        public void method_with_path() {
        }

        @RequestMapping(method = RequestMethod.GET, value = "/sub/{id}")
        public void method_with_value() {
        }
    }

    @RestController
    private static class NoMappingController {
        @RequestMapping(method = RequestMethod.GET, path = "/about")
        public void method_with_path() {
        }
    }

    private String transactionNameFor(Class<?> controllerClass, String methodName) {
        Optional<String> name = transactionNameOf(controllerClass, methodRef(controllerClass, methodName));
        return name.get();
    }

}