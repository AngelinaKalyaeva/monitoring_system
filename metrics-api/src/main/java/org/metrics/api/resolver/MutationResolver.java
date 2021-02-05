package org.metrics.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.metrics.api.model.Efficiency;
import org.metrics.api.model.Metrics;
import org.metrics.api.model.Payment;
import org.metrics.api.model.Service;
import org.springframework.stereotype.Component;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    public Metrics writePaymentSystemMetric(String serviceId, String code , String type) {
        return new Metrics(new Service(serviceId, 100, new Efficiency(new Payment(code, type))));
    }
}
