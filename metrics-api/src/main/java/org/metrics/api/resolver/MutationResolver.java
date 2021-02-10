package org.metrics.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.metrics.api.model.*;
import org.metrics.api.model.Error;
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

    public Metrics writePaymentSystemMetric(String serviceId, String code, String type) {
        Metrics metrics = Metrics.builder()
                .service(
                        Service.builder()
                                .id(serviceId)
                                .datetime(
                                        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                ).efficiency(
                                Efficiency.builder()
                                        .payment(
                                                Payment.builder().code(code).type(type).build()
                                        ).build()
                        ).build()
                ).build();

        efficientMetricsService.writePaymentSystemMetric(metrics);
        return metrics;
    }

    public Metrics writeSlaError(String serviceId, String url, String code) {
        Metrics metrics = Metrics.builder()
                .service(
                        Service.builder()
                                .id(serviceId)
                                .datetime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                                .efficiency(
                                        Efficiency.builder()
                                                  .sla(
                                                        Sla.builder()
                                                           .url(url)
                                                           .error(
                                                                   Error.builder()
                                                                        .code(code)
                                                                        .build()
                                                           ).build()
                                                  ).build()
                                        ).build()
                ).build();

        efficientMetricsService.writeSlaError(metrics);
        return metrics;
    }
}
