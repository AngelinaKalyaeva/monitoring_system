package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Database {
    private String query;
    private Error error;
    private Timing timing;
}
