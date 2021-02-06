package org.metrics.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.metrics.api.model.Efficiency;
import org.metrics.api.model.Metrics;
import org.metrics.api.model.Payment;
import org.metrics.api.model.Service;
import org.metrics.api.service.EfficientMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    private final EfficientMetricsService efficientMetricsService;

    @Autowired
    public MutationResolver(EfficientMetricsService efficientMetricsService) {
        this.efficientMetricsService = efficientMetricsService;
    }

    public Metrics writePaymentSystemMetric(String serviceId, String code , String type) {
        Metrics metrics = new Metrics(
                new Service(
                        serviceId,
                        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                        new Efficiency(
                                new Payment(
                                        code,
                                        type
                                )
                        )
                )
        );
        efficientMetricsService.writePaymentSystemMetric(metrics);
        return metrics;
    }
}
