package com.aconex.monitoring.prometheus.utils;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface Controllers {
    @RestController
    @RequestMapping("/sample")
    class MappingController {
        @RequestMapping(method = POST)
        public void without_path() {
        }

        @RequestMapping(method = GET, path = "/with_path/{id}")
        public Object with_path() {
            return null;
        }

        @RequestMapping(method = GET, value = "/with_value/{id}")
        public Object with_value() {
            return null;
        }

        @RequestMapping(value = "/with_value/{id}")
        public Object without_http_method() {
            return null;
        }

    }

    @RestController
    class NoMappingController {
        @RequestMapping(method = GET, path = "/with_path")
        public void with_path() {
        }

        @RequestMapping(method = GET, value = "/with_value")
        public Object with_value() {
            return null;
        }
    }
}
