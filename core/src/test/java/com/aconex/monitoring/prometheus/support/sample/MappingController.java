package com.aconex.monitoring.prometheus.support.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class MappingController {
    @RequestMapping(method = RequestMethod.POST)
    public void without_path() {
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sub/{id}")
    public void with_path() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sub/{id}")
    public void with_value() {
    }
}
