package org.ko.task.model;

public class LoyaltyStampProgram {

    private String customerId;
    private int stamps;

    public LoyaltyStampProgram() {
    }

    public LoyaltyStampProgram(String customerId, int stamps) {
        this.customerId = customerId;
        this.stamps = stamps;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getStamps() {
        return stamps;
    }

    public void setStamps(int stamps) {
        this.stamps = stamps;
    }
}
