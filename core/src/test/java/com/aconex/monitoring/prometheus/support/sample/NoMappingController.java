package com.aconex.monitoring.prometheus.support.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoMappingController {
    @RequestMapping(method = RequestMethod.GET, path = "/about")
    public void method_with_path() {
    }
}
