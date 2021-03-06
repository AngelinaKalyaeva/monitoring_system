package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sale {
    Integer cost;
    Integer saleCount;
    Integer returnCount;
}
