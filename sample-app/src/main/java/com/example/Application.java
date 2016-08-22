package com.example;

import com.github.nicholasren.monitoring.prometheus.collector.RequestStatsCollector;
import com.github.nicholasren.monitoring.prometheus.collector.TransactionLatencyCollector;
import com.github.nicholasren.monitoring.prometheus.config.MonitoringConfig;
import com.example.monitor.MetricAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MonitoringConfig.class)
public class Application {

    //TODO: integration for request stats
    @Bean
    public FilterRegistrationBean requestStatsCollector() {
        return RequestStatsCollector.create("/*", 100);
    }
    //TODO: integration for transaction latency
    @Bean
    public MetricAspect MetricAspect() {
        return new MetricAspect(TransactionLatencyCollector.create());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
