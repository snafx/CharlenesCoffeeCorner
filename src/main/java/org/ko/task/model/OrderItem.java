package org.ko.task.model;

import java.text.DecimalFormat;

public class OrderItem {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private int quantity;
    private Product product;
    private Product productExtra;

    public OrderItem(int quantity, Product product, Product productExtra) {
        this.quantity = quantity;
        this.product = product;
        this.productExtra = productExtra;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProductExtra() {
        return productExtra;
    }

    public void setProductExtra(Product productExtra) {
        this.productExtra = productExtra;
    }

    @Override
    public String toString() {
        String paddedProductName = String.format("%-40s", product.getProductName() + (productExtra != null ? " with " + productExtra.getProductName() : ""));
        String paddedSingleValue = String.format("%-8s", df.format(product.getProductPrice() + (productExtra != null ? productExtra.getProductPrice() : 0)));
        String paddedTotalValue = String.format("%-8s", df.format(quantity * product.getProductPrice() + (productExtra != null ? quantity * productExtra.getProductPrice() : 0)));
        return "\n[" + paddedProductName + " | amount: " + quantity + " | Price: CHF " + paddedSingleValue + " | Total: CHF " + paddedTotalValue + "]";
    }
}
