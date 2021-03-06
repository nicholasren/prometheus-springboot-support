package com.example;

import com.github.nicholasren.monitoring.prometheus.config.MonitoringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MonitoringConfig.class)
public final class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private Application() {
    }
}
