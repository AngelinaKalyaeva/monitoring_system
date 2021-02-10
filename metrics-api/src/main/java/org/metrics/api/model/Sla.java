package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sla {
    private String url;
    private Error error;
}
