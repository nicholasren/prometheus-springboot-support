package com.github.nicholasren.monitoring.prometheus.config;

import javax.annotation.PostConstruct;

import com.github.nicholasren.monitoring.prometheus.collector.RequestStatsCollector;
import com.github.nicholasren.monitoring.prometheus.collector.TransactionLatencyCollector;
import com.github.nicholasren.monitoring.prometheus.collector.TransactionLatencyAspect;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfig {
    @Bean
    public ServletRegistrationBean registerPrometheusExporterServlet() {
        return new ServletRegistrationBean(new MetricsServlet(CollectorRegistry.defaultRegistry), "/metrics");
    }

    @Bean
    public FilterRegistrationBean requestStatsCollector() {
        return RequestStatsCollector.create("/*", 100);
    }

    @Bean
    public TransactionLatencyAspect aspect(TransactionLatencyCollector collector) {
        return new TransactionLatencyAspect(collector);
    }

    @Bean
    public TransactionLatencyCollector collector() {
        return TransactionLatencyCollector.create();
    }

    @PostConstruct
    public void defaultExports() {
        DefaultExports.initialize();
    }
}
