### Prometheus SprintBoot Bridge

---
This component providing few built in metrics for springboot based web application.


#### Getting Started
- Add dependency:
```groovy
    compile: com.github.nicholasren:prometheus-springboot-support:0.0.1
```
- Import monitoring config:
```java
    @Import(com.github.nicholasren.monitoring.prometheus.config.MonitoringConfig.class)
```

- Annotate controllers with `com.github.nicholasren.monitoring.prometheus.annotations.MonitoredController`:
e.g.
```java
@RestController
@RequestMapping("/hello")
@Monitor
public class HelloController {
}
```

- Start your application and view metrics are exposed via `http://localhost:8080/<context-path>/metrics`.

