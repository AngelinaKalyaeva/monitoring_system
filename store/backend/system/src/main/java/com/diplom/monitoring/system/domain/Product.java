package com.diplom.monitoring.system.domain;

public class Product {
    long id;
    String name;
    long price;
    String currency;
    String description;
    boolean availability;
    long count;

    public Product() {
    }

    public Product(long id, String name, long price, String currency, String description, boolean availability, long count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.description = description;
        this.availability = availability;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
