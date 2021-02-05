package org.metrics.api.model;

public class Service {
    private String id;
    private Integer datetime;
    private Efficiency efficiency;

    public Service(String id, Integer datetime, Efficiency efficiency) {
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

    public Integer getDatetime() {
        return datetime;
    }

    public void setDatetime(Integer datetime) {
        this.datetime = datetime;
    }

    public Efficiency getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Efficiency efficiency) {
        this.efficiency = efficiency;
    }
}
