package org.metrics.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Attendance {
    private String url;
    private Integer count;
    private Long time;
}
