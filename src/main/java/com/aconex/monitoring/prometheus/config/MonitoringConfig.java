package com.aconex.monitoring.prometheus.config;

import javax.annotation.PostConstruct;

import com.aconex.monitoring.prometheus.collector.RequestStatsCollector;
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
    public FilterRegistrationBean requestStatsCollector(String urlPattern, int order) {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new RequestStatsCollector());
        bean.addUrlPatterns(urlPattern);
        bean.setOrder(order);
        return bean;
    }

    @PostConstruct
    public void defaultExports() {
        DefaultExports.initialize();
    }
}
