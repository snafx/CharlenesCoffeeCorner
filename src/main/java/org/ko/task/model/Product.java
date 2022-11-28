package org.ko.task.model;

import java.text.DecimalFormat;

public class Product {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private int id;
    private String productName;
    private Double productPrice;
    private ProductType productType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        String paddedProductName = String.format("%-10s", productType);
        String paddedProduct = String.format("%-45s",  productName);
        String paddedPrice = String.format("%-13s", " | CHF " + df.format(productPrice));
        return id + " | " + paddedProductName + " | " + paddedProduct + paddedPrice +  "| ";
    }
}
