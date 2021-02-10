package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Efficiency {
    private Payment payment;
    private Sla sla;
}
