package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.nicholasren.monitoring.prometheus.config.MonitoringConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@SpringApplicationConfiguration(value = {Application.class, MonitoringConfig.class})
public class MetricsTest extends AbstractJUnit4SpringContextTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_expose_metrics() throws Exception {
        this.mvc.perform(get("/hello/world/earth"))
                .andExpect(status().isOk())
                .andExpect(content().string("Greetings from World earth"));

        this.mvc.perform(get("/metrics"))
                .andExpect(status().is(200))
                .andDo(print());
    }

}
