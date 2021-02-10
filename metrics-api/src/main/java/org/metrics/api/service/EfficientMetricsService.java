package org.metrics.api.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.metrics.api.model.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EfficientMetricsService {
    private final MeterRegistry meterRegistry;

    @Autowired
    public EfficientMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void writePaymentSystemMetric(Metrics metrics) {
        meterRegistry.counter(
                "payment_error",
                "service_id", metrics.getService().getId(),
                "status", metrics.getService().getEfficiency().getPayment().getCode(),
                "type", metrics.getService().getEfficiency().getPayment().getType()
        ).increment();
    }

    public void writeSlaError(Metrics metrics) {
        meterRegistry.counter(
                "url_error",
                "service_id", metrics.getService().getId(),
                "url", metrics.getService().getEfficiency().getSla().getUrl(),
                "code", metrics.getService().getEfficiency().getSla().getError().getCode()
        ).increment();
    }
}
