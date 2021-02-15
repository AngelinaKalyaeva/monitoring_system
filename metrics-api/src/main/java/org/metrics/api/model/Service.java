package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Service {
    private String id;
    private Long datetime;
    private Efficiency efficiency;
    private Analytics analytics;
}
