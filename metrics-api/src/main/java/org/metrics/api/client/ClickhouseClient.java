package org.metrics.api.client;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Data
@Component
public class ClickhouseClient {
    private final String CLICKHOUSE_BASE_URL = "http://clickhouse:8123/ping";
    private final RestTemplate restTemplate;

    public void pingClickHouse() {
        String pingAnswer = restTemplate.getForObject(CLICKHOUSE_BASE_URL, String.class);
        System.out.println(pingAnswer);
    }
}
