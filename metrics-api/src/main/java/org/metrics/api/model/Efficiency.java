package org.metrics.api.model;

public class Efficiency {
    private Payment payment;

    public Efficiency(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
