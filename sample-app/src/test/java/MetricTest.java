import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

import com.example.Application;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;

@RunWith(JUnitPlatform.class)
public class MetricTest {

    public static final String METRICS_URL = "http://localhost:9090/sample/metrics";

    @BeforeAll
    public static void setup() {
        SpringApplication.run(Application.class);
    }

    @Test
    public void should_expose_default_metrics() {
        get(METRICS_URL)
                .then()
                .body(containsString("jvm_gc_collection_seconds"));
    }

    @Test
    public void should_expose_transaction_latency_metrics() {
        get("http://localhost:9090/sample/hello/world/earth")
                .then()
                .body(equalTo("Greetings from World earth"));

        get(METRICS_URL)
                .then()
                .body(containsString("controller_latency_seconds_bucket{transaction=\"GET /hello/world/{id}\""));

    }
}
