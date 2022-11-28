package org.ko.task.model;

import java.util.List;

public class Order {

    private int orderId;
    private String orderDate;
    private LoyaltyStampProgram loyaltyStampProgram;
    private List<OrderItem> orderItemsList;
    private Double totalOrderValue = 0.0;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public LoyaltyStampProgram getLoyaltyStampProgram() {
        return loyaltyStampProgram;
    }

    public void setLoyaltyStampProgram(LoyaltyStampProgram loyaltyStampProgram) {
        this.loyaltyStampProgram = loyaltyStampProgram;
    }

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(Double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

}
