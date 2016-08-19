package com.aconex.monitoring.prometheus.support.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoMappingController {
    @RequestMapping(method = RequestMethod.GET, path = "/with_path")
    public void with_path() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/with_value")
    public Object with_value() {
        return null;
    }
}
