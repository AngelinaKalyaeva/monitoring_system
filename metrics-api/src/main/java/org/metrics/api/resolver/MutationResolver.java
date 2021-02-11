package org.metrics.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Data;
import org.metrics.api.model.Efficiency;
import org.metrics.api.model.Error;
import org.metrics.api.model.Metrics;
import org.metrics.api.model.Payment;
import org.metrics.api.model.Service;
import org.metrics.api.model.Sla;
import org.metrics.api.model.Timing;
import org.metrics.api.service.AnaliticsMetricsService;
import org.metrics.api.service.EfficientMetricsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Component
public class MutationResolver implements GraphQLMutationResolver {
    private final EfficientMetricsService efficientMetricsService;
    private final AnaliticsMetricsService analiticsMetricsService;

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

    public Metrics writeSlaTiming(String serviceId, String url, Integer time, Integer slaTime) {
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
                                                                .timing(
                                                                        Timing.builder()
                                                                                .time(time)
                                                                                .slaTime(slaTime)
                                                                                .build()
                                                                ).build()
                                                ).build()
                                ).build()
                ).build();

        efficientMetricsService.writeSlaTiming(metrics);
        return metrics;
    }

    public Metrics writeAnalitics(String serviceId) {
        analiticsMetricsService.pingClickhouse();
        return Metrics.builder().service(Service.builder().id(serviceId).build()).build();
    }
}
