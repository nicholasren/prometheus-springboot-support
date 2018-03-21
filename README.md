### Prometheus SprintBoot Bridge

---
This component providing few built in metrics for springboot based web application.

#### Prerequisites
- Springboot 2.0.0

for old version of springboot, use `0.0.3` 

#### Getting Started
- Add dependency in `build.gradle`:
```groovy
    compile: 'com.github.nicholasren:prometheus-springboot-support:0.0.4'
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
@MonitoredController
public class HelloController {
}
```

- Start your application and view metrics are exposed via `http://<path-to-your-app>/metrics`.

#### Get snapshot version
To use snapshot version, add the following repo in `build.gradle`

```groovy
repositories {
  maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
}
```
