package org.metrics.api.service;

import lombok.Data;
import org.metrics.api.client.ClickhouseClient;
import org.springframework.stereotype.Service;

@Data
@Service
public class AnaliticsMetricsService {
    private final ClickhouseClient clickhouseClient;

    public void writeAttendancyMetrics(String serviceId, String url, Integer count) {
        clickhouseClient.writeAttendancyMetrics(serviceId, url, count);
    }
}
