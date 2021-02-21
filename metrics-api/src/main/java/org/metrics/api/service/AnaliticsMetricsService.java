package org.metrics.api.service;

import lombok.Data;
import org.metrics.api.client.ClickhouseClient;
import org.metrics.api.model.Metrics;
import org.springframework.stereotype.Service;

@Data
@Service
public class AnaliticsMetricsService {
    private final ClickhouseClient clickhouseClient;

    public void writeAttendancyMetrics(String serviceId, String url, Integer count) {
        clickhouseClient.writeAttendancyMetrics(serviceId, url, count);
    }

    public void writeSaleDynamic(String serviceId, Integer saleCount, Integer returnCount, Integer cost) {
        clickhouseClient.writeSaleDynamicMetrics(serviceId, saleCount, returnCount, cost);
    }

    public void writeProductDynamic(Metrics metrics) {
        clickhouseClient.writeProductDynamicMetrics(metrics);
    }
}
