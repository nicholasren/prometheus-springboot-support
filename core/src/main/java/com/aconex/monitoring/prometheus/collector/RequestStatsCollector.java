package com.aconex.monitoring.prometheus.collector;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.web.filter.GenericFilterBean;

public class RequestStatsCollector extends GenericFilterBean {

    private static final Counter HTTP_RESPONSE_STATUS_TOTAL = Counter.build()
            .name("http_response_status_total")
            .labelNames("status")
            .help("processed requests labeled by status")
            .register();

    private static final Gauge INPROGRESS_REQUESTS = Gauge.build()
            .name("inprogress_requests")
            .help("Inprogress requests.")
            .register();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            INPROGRESS_REQUESTS.inc();
            chain.doFilter(request, response);
        } finally {
            INPROGRESS_REQUESTS.dec();
            HTTP_RESPONSE_STATUS_TOTAL.labels(statusOf(response)).inc();
        }
    }

    public static FilterRegistrationBean create(String pattern, int order) {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new RequestStatsCollector());
        bean.addUrlPatterns(pattern);
        bean.setOrder(order);
        return bean;
    }

    private String statusOf(ServletResponse response) {
        return String.valueOf(((HttpServletResponse) response).getStatus());
    }
}
