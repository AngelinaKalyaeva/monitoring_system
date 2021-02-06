package org.metrics.api.model;

public class Service {
    private String id;
    private Long datetime;
    private Efficiency efficiency;

    public Service(String id, Long datetime, Efficiency efficiency) {
        this.id = id;
        this.datetime = datetime;
        this.efficiency = efficiency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public Efficiency getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Efficiency efficiency) {
        this.efficiency = efficiency;
    }
}
