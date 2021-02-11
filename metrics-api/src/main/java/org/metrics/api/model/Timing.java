package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Timing {
    Integer time;
    Integer slaTime;
}
