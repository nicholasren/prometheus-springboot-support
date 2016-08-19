package com.aconex.monitoring.prometheus.support.sample;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class MappingController {
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

}
