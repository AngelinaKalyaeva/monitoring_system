package org.metrics.api.client;

import lombok.Data;
import org.metrics.api.model.Metrics;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Component
public class ClickhouseClient {
    private final String CLICKHOUSE_BASE_URL = "http://clickhouse:8123?query=";
    private final RestTemplate restTemplate;

    private final String ATTENDANCE_METRICS_QUERY =
            "INSERT INTO analytics_metrics.attendance VALUES (%s, %s, %s, %s)";
    private final String DYNAMIC_SALE_METRICS_QUERY =
            "INSERT INTO analytics_metrics.dynamic_sale VALUES (%s, %s, %s, %s, %s);";
    private final String PRODUCT_DYNAMIC_METRICS_QUERY =
            "INSERT INTO analytics_metrics.product_dynamic VALUES (%s, %s, %s, %s);";

    public void writeAttendancyMetrics(String serviceId, String url, Integer count) {
        restTemplate.postForObject(
                CLICKHOUSE_BASE_URL.concat(
                        String.format(
                                ATTENDANCE_METRICS_QUERY,
                                "'".concat(serviceId).concat("'"),
                                "'".concat(url).concat("'"),
                                count.toString(),
                                LocalDateTime.now().toEpochSecond(ZoneOffset.of("+00:00"))
                        )
                ),
                "",
                String.class
        );
    }

    public void writeSaleDynamicMetrics(String serviceId, Integer saleCount, Integer returnCount, Integer cost) {
        restTemplate.postForObject(
                CLICKHOUSE_BASE_URL.concat(
                        String.format(
                                DYNAMIC_SALE_METRICS_QUERY,
                                "'".concat(serviceId).concat("'"),
                                cost,
                                saleCount,
                                returnCount,
                                LocalDateTime.now().toEpochSecond(ZoneOffset.of("+00:00"))
                        )
                ),
                "",
                String.class
        );
    }

    public void writeProductDynamicMetrics(Metrics metrics) {
        restTemplate.postForObject(
                CLICKHOUSE_BASE_URL.concat(
                        String.format(
                                PRODUCT_DYNAMIC_METRICS_QUERY,
                                "'".concat(metrics.getService().getId()).concat("'"),
                                metrics.getService().getAnalytics().getDynamic().getProduct().getId(),
                                metrics.getService().getAnalytics().getDynamic().getProduct().getPopularity().getSalesCount(),
                                LocalDateTime.now().toEpochSecond(ZoneOffset.of("+00:00"))
                        )
                ),
                "",
                String.class
        );
    }
}
