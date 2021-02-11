package org.metrics.api.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.metrics.api.model.Metrics;
import org.metrics.api.model.gauge.SlaTime;
import org.metrics.api.model.gauge.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EfficientMetricsService {
    private final SlaTime slaTime = new SlaTime(new HashMap<>());
    private final Time time = new Time(new HashMap<>());
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

    public void writeSlaTiming(Metrics metrics) {
        Integer slaTime = metrics.getService().getEfficiency().getSla().getTiming().getSlaTime();
        String url = metrics.getService().getEfficiency().getSla().getUrl();
        if (slaTime != null) {
            this.slaTime.getTime().put(url, slaTime.doubleValue());
            Gauge.builder("sla_time", this.slaTime, el -> el.getTime().get(url)).tags(
                    "service_id", metrics.getService().getId(),
                    "url", url
            ).register(meterRegistry);
        }

        Integer time = metrics.getService().getEfficiency().getSla().getTiming().getTime();
        if (time != null) {
            this.time.getTime().put(url, time.doubleValue());
            Gauge.builder("url_time", this.time, el -> el.getTime().get(url)).tags(
                    "service_id", metrics.getService().getId(),
                    "url", url
            ).register(meterRegistry);
        }
    }
}
