package org.metrics.api.client;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Component
public class ClickhouseClient {
    private final String CLICKHOUSE_BASE_URL = "http://192.168.99.101:8123?query=";
    private final RestTemplate restTemplate;

    private final String ATTENDANCE_METRICS_QUERY = "INSERT INTO analytics_metrics.attendance VALUES (%s, %s, %s, %s)";

    public void writeAttendancyMetrics(String serviceId, String url, Integer count) {
        String pingAnswer = restTemplate.postForObject(
                CLICKHOUSE_BASE_URL.concat(
                        String.format(
                                ATTENDANCE_METRICS_QUERY,
                                "'".concat(serviceId).concat("'"),
                                "'".concat(url).concat("'"),
                                count.toString(),
                                LocalDateTime.now().toEpochSecond(ZoneOffset.of("+03:00"))
                        )
                ),
                "",
                String.class
        );
    }
}
