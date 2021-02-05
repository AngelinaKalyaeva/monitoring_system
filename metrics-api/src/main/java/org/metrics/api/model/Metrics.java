package org.metrics.api.model;

public class Metrics {
    private Service service;

    public Metrics(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
