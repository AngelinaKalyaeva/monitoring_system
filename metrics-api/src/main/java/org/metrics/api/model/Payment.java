package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
    private String code;
    private String type;
}
