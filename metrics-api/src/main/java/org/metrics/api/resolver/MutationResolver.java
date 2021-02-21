package org.metrics.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.Data;
import org.metrics.api.model.*;
import org.metrics.api.model.Error;
import org.metrics.api.service.AnaliticsMetricsService;
import org.metrics.api.service.EfficientMetricsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.*;

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
                                        LocalDateTime.now().toEpochSecond(UTC)
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
                                .datetime(LocalDateTime.now().toEpochSecond(UTC))
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

    public Metrics writeDatabaseError(String serviceId, String query, String code) {
        Metrics metrics = Metrics.builder()
                .service(
                        Service.builder()
                                .id(serviceId)
                                .datetime(LocalDateTime.now().toEpochSecond(UTC))
                                .efficiency(
                                        Efficiency.builder()
                                                .database(
                                                        Database.builder()
                                                                .query(query)
                                                                .error(
                                                                        Error.builder()
                                                                                .code(code)
                                                                                .build()
                                                                ).build()
                                                ).build()
                                ).build()
                ).build();

        efficientMetricsService.writeDatabaseError(metrics);
        return metrics;
    }

    public Metrics writeSlaTiming(String serviceId, String url, Integer time, Integer slaTime) {
        Metrics metrics = Metrics.builder()
                .service(
                        Service.builder()
                                .id(serviceId)
                                .datetime(LocalDateTime.now().toEpochSecond(UTC))
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

    public Metrics writeDatabaseTiming(String serviceId, String query, Integer time, Integer slaTime) {
        Metrics metrics = Metrics.builder()
                .service(
                        Service.builder()
                                .id(serviceId)
                                .datetime(LocalDateTime.now().toEpochSecond(UTC))
                                .efficiency(
                                        Efficiency.builder()
                                                .database(
                                                        Database.builder()
                                                                .query(query)
                                                                .timing(
                                                                        Timing.builder()
                                                                                .time(time)
                                                                                .slaTime(slaTime)
                                                                                .build()
                                                                ).build()
                                                ).build()
                                ).build()
                ).build();

        efficientMetricsService.writeDatabaseTiming(metrics);
        return metrics;
    }

    public Metrics writeAttendancy(String serviceId, String url, Integer count) {
        analiticsMetricsService.writeAttendancyMetrics(serviceId, url, count);
        return Metrics.builder().service(
                Service.builder().id(serviceId).datetime(LocalDateTime.now().toEpochSecond(UTC)).analytics(
                        Analytics.builder().attendance(
                                Attendance.builder()
                                        .url(url)
                                        .count(count)
                                        .build()
                        ).build()
                ).build()
        ).build();
    }

    public Metrics writeSaleDynamic(String serviceId, Integer saleCount, Integer returnCount, Integer cost) {
        analiticsMetricsService.writeSaleDynamic(serviceId, saleCount, returnCount, cost);
        return Metrics.builder().service(
                Service.builder().id(serviceId).datetime(LocalDateTime.now().toEpochSecond(UTC)).analytics(
                        Analytics.builder().dynamic(
                          Dynamic.builder().sale(
                                  Sale.builder()
                                          .saleCount(saleCount)
                                          .returnCount(returnCount)
                                          .build()
                          ).build()
                        ).build()
                ).build()
        ).build();
    }

    public Metrics writeProductDynamic(String serviceId, String productId, Integer salesCount) {
        Metrics metrics = Metrics.builder().service(
                Service.builder().id(serviceId).datetime(LocalDateTime.now().toEpochSecond(UTC)).analytics(
                        Analytics.builder().dynamic(
                                Dynamic.builder().product(
                                        Product.builder()
                                                .id(productId)
                                                .popularity(
                                                        Popularity.builder()
                                                                .salesCount(salesCount)
                                                                .build()
                                                ).build()
                                ).build()
                        ).build()
                ).build()
        ).build();

        analiticsMetricsService.writeProductDynamic(metrics);
        return metrics;
    }
}
