package org.metrics.api.model.gauge;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class SlaTime {
    private Map<String, Double> time;
}
