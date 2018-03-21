package com.example.controllers;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.github.nicholasren.monitoring.prometheus.annotations.MonitoredController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@MonitoredController
public class HelloController {

    public HelloController() {
        System.out.println("initing");
    }

    @RequestMapping(method = GET, path = "/world/{id}")
    @ResponseBody
    public String world(@PathVariable("id") String id) {
        return format("Greetings from World %s", id);
    }

    @RequestMapping(method = GET, path = "/haha")
    @ResponseBody
    public String haha() {
        return "haha";
    }
}
