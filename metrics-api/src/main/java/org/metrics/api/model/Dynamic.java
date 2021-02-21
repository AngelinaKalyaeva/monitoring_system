package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dynamic {
    Sale sale;
    Product product;
}
