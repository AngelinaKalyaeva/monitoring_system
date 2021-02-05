package org.metrics.api.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.metrics.api.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    public Payment getPaymentByCode(String code) {
        return new Payment(code, "visa");
    }
}
