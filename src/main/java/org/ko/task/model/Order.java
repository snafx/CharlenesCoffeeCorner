package org.ko.task.model;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class Order {

    public static final String EMPTY_LINE = new String(new char[100]).replace('\0', '-');
    public static final String DEFAULT_FORMAT = "|%-36s %-24s %-36s|%n";
    private static final DecimalFormat df = new DecimalFormat("0.00");
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

    public void printOrder() {
        System.out.println(EMPTY_LINE);
        System.out.printf(DEFAULT_FORMAT, "", "Charlene's Coffee Corner", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf("|%-38s %-19s %-39s|%n", "", "Zürich Hauptbahnhof", "");
        System.out.printf("|%-36s %-25s %-35s|%n", "", "Bahnhofplatz, 8001 Zürich", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf("|%-42s %-12s %-42s|%n", "", "Order id: " + orderId, "");
        System.out.printf("|%-38s %-20s %-38s|%n", "", orderDate, "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.println(EMPTY_LINE);
        orderItemsList.forEach(orderItem -> {
            String paddedProductName = orderItem.getProduct().getProductName() + (orderItem.getProductExtra() != null ? " with " + orderItem.getProductExtra().getProductName() : "");
            String paddedSingleValue = df.format(orderItem.getProduct().getProductPrice() + (orderItem.getProductExtra() != null ? orderItem.getProductExtra().getProductPrice() : 0));
            String paddedTotalValue = df.format(orderItem.getQuantity() * orderItem.getProduct().getProductPrice() + (orderItem.getProductExtra() != null ? orderItem.getQuantity() * orderItem.getProductExtra().getProductPrice() : 0));
            System.out.printf("| %-44s | %-10s | %-16s | %-18s|%n", paddedProductName, "amount: " + orderItem.getQuantity(), "Price: CHF " + paddedSingleValue, "Total: CHF " + paddedTotalValue);
        });
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.println(EMPTY_LINE);
        System.out.printf("|%78s | %-16s |%n", "", "TOTAL: CHF " + df.format(calculateTotalBillCost(orderItemsList)));
        System.out.println(EMPTY_LINE);
        System.out.println(EMPTY_LINE);
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf("|%69s | %-16s |%n", "", "Loyalty program stamps: " + loyaltyStampProgram.getStamps());
        if (loyaltyStampProgram.getStamps() == 5) {
            System.out.printf("|%45s | %-16s |%n", "", "You have 5 stamps! You next beverage is for free!");
            System.out.printf(DEFAULT_FORMAT, "", "", "");
        }
        System.out.println(EMPTY_LINE);
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf("|%-26s %-44s %-26s|%n", "", "Thank you for stopping by. Have a great day!", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.println(EMPTY_LINE);
    }

    private Double calculateTotalBillCost(List<OrderItem> orderedProducts) {
        orderedProducts.forEach(orderedProduct -> {
            double productPrice = orderedProduct.getProduct().getProductPrice();
            double productExtraPrice = orderedProduct.getProductExtra() != null ? orderedProduct.getProductExtra().getProductPrice() : 0.0;
            int quantity = orderedProduct.getQuantity();
            double totalProductPrice = quantity * productPrice + quantity * productExtraPrice;
            totalOrderValue += totalProductPrice;
        });
        checkIfEligibleForComboMealDeal(orderedProducts);
        checkIfEligibleForFreeBeverage(loyaltyStampProgram, orderedProducts);

        System.out.println(EMPTY_LINE);
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        return totalOrderValue;
    }

    private void checkIfEligibleForFreeBeverage(LoyaltyStampProgram loyaltyStampProgram, List<OrderItem> orderedProducts) {
        while (loyaltyStampProgram.getStamps() > 5) {
            calculateDiscount(loyaltyStampProgram, orderedProducts);
        }
    }

    private void calculateDiscount(LoyaltyStampProgram loyaltyStampProgram, List<OrderItem> orderedProducts) {
        OrderItem cheapestBeverage = orderedProducts.stream()
                .filter(orderItem -> orderItem.getProduct().getProductType().equals(ProductType.BEVERAGE))
                .min(Comparator.comparingDouble(product -> product.getProduct().getProductPrice()))
                .orElse(null);
        if (cheapestBeverage != null) {

            System.out.println(EMPTY_LINE);
            totalOrderValue -= cheapestBeverage.getProduct().getProductPrice();
            System.out.printf("|%75s | %-16s %-2s |%n", "", "Stamps before: ", loyaltyStampProgram.getStamps());
            System.out.printf("|%54s | %-16s |%n", "", "Loyalty program free beverage: CHF -" + df.format(cheapestBeverage.getProduct().getProductPrice()));
            loyaltyStampProgram.setStamps(loyaltyStampProgram.getStamps() - 5);
            System.out.printf("|%75s | %-16s %-2s |%n", "", "Stamps left: ", loyaltyStampProgram.getStamps());
        }
    }

    private void checkIfEligibleForComboMealDeal(List<OrderItem> orderedProducts) {
        long countBeverages = orderedProducts.stream()
                .filter(orderItem -> orderItem.getProduct().getProductType().equals(ProductType.BEVERAGE))
                .findAny().stream().count();

        long countSnacks = orderedProducts.stream()
                .filter(orderItem -> orderItem.getProduct().getProductType().equals(ProductType.SNACK))
                .findAny().stream().count();

        if (countBeverages > 0 && countSnacks > 0) {
            OrderItem cheapestExtras = orderedProducts.stream()
                    .filter(orderItem -> orderItem.getProductExtra() != null)
                    .min(Comparator.comparingDouble(product -> product.getProductExtra().getProductPrice()))
                    .orElse(null);
            if (cheapestExtras != null) {
                System.out.printf("|%64s | %-16s |%n", "", "Combo meal discount: CHF -" + df.format(cheapestExtras.getProductExtra().getProductPrice()));
                totalOrderValue -= cheapestExtras.getProductExtra().getProductPrice();
            }
        }
    }

}
