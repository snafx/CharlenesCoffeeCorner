package org.ko.task.services;

import org.ko.task.model.LoyaltyStampProgram;
import org.ko.task.model.Order;
import org.ko.task.model.OrderItem;
import org.ko.task.model.ProductType;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderServices {

    public static final String EMPTY_LINE = new String(new char[100]).replace('\0', '-');
    public static final String DEFAULT_FORMAT = "|%-36s %-24s %-36s|%n";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    List<OrderItem> orderedProducts = new ArrayList<>();


    public Order createOrder(List<OrderItem> orderItemList) {
        System.out.println("CUSTOMER RECEIPT:\n");

        Order order = new Order();
        order.setOrderId(1);
        order.setOrderDate(getOrderDateAndTime());

        orderItemList.forEach(orderItem -> addProductToOrder(new OrderItem(orderItem.getQuantity(), orderItem.getProduct(), orderItem.getProductExtra())));

        order.setOrderItemsList(orderedProducts);
        updateLoyaltyStampProgram(order, orderedProducts);
        printOrder(order);

        return order;
    }

    String getOrderDateAndTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void addProductToOrder(OrderItem orderItem) {
        orderedProducts.add(orderItem);
    }

    public void updateLoyaltyStampProgram(Order order, List<OrderItem> orderedProducts) {
        LoyaltyStampProgram loyaltyStampProgram = new LoyaltyStampProgram();
        loyaltyStampProgram.setCustomerId("1");
        orderedProducts.forEach(product -> {
            boolean equals = product.getProduct().getProductType().equals(ProductType.BEVERAGE);
            if (equals) {
                loyaltyStampProgram.setStamps(loyaltyStampProgram.getStamps() + product.getQuantity());
            }
        });
        order.setLoyaltyStampProgram(loyaltyStampProgram);
    }

    public void printOrder(Order order) {
        System.out.println(EMPTY_LINE);
        System.out.printf(DEFAULT_FORMAT, "", "Charlene's Coffee Corner", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf("|%-38s %-19s %-39s|%n", "", "Zürich Hauptbahnhof", "");
        System.out.printf("|%-36s %-25s %-35s|%n", "", "Bahnhofplatz, 8001 Zürich", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf("|%-42s %-12s %-42s|%n", "", "Order id: " + order.getOrderId(), "");
        System.out.printf("|%-38s %-20s %-38s|%n", "", order.getOrderDate(), "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.println(EMPTY_LINE);
        order.getOrderItemsList().forEach(orderItem -> {
            String paddedProductName = orderItem.getProduct().getProductName() + (orderItem.getProductExtra() != null ? " with " + orderItem.getProductExtra().getProductName() : "");
            String paddedSingleValue = df.format(orderItem.getProduct().getProductPrice() + (orderItem.getProductExtra() != null ? orderItem.getProductExtra().getProductPrice() : 0));
            String paddedTotalValue = df.format(orderItem.getQuantity() * orderItem.getProduct().getProductPrice() + (orderItem.getProductExtra() != null ? orderItem.getQuantity() * orderItem.getProductExtra().getProductPrice() : 0));
            System.out.printf("| %-44s | %-10s | %-16s | %-18s|%n", paddedProductName, "amount: " + orderItem.getQuantity(), "Price: CHF " + paddedSingleValue, "Total: CHF " + paddedTotalValue);
        });
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.println(EMPTY_LINE);
        System.out.printf("|%78s | %-16s |%n", "", "TOTAL: CHF " + df.format(calculateTotalBillCost(order)));
        System.out.println(EMPTY_LINE);
        System.out.println(EMPTY_LINE);
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        System.out.printf("|%69s | %-16s |%n", "", "Loyalty program stamps: " + order.getLoyaltyStampProgram().getStamps());
        if (order.getLoyaltyStampProgram().getStamps() == 5) {
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

    public Double calculateTotalBillCost(Order order) {
        order.getOrderItemsList().forEach(orderedProduct -> {
            double productPrice = orderedProduct.getProduct().getProductPrice();
            double productExtraPrice = orderedProduct.getProductExtra() != null ? orderedProduct.getProductExtra().getProductPrice() : 0.0;
            int quantity = orderedProduct.getQuantity();
            double totalProductPrice = quantity * productPrice + quantity * productExtraPrice;
            order.setTotalOrderValue(order.getTotalOrderValue() + totalProductPrice);
        });
        checkIfEligibleForComboMealDeal(order);
        checkIfEligibleForFreeBeverage(order);

        System.out.println(EMPTY_LINE);
        System.out.printf(DEFAULT_FORMAT, "", "", "");
        return order.getTotalOrderValue();
    }

    public void checkIfEligibleForFreeBeverage(Order order) {
        while (order.getLoyaltyStampProgram().getStamps() > 5) {
            calculateDiscount(order);
        }
    }

    private void calculateDiscount(Order order) {
        OrderItem cheapestBeverage = orderedProducts.stream()
                .filter(orderItem -> orderItem.getProduct().getProductType().equals(ProductType.BEVERAGE))
                .min(Comparator.comparingDouble(product -> product.getProduct().getProductPrice()))
                .orElse(null);
        if (cheapestBeverage != null) {
            System.out.println(EMPTY_LINE);
            order.setTotalOrderValue(order.getTotalOrderValue() - cheapestBeverage.getProduct().getProductPrice());
            System.out.printf("|%75s | %-16s %-2s |%n", "", "Stamps before: ", order.getLoyaltyStampProgram().getStamps());
            System.out.printf("|%54s | %-16s |%n", "", "Loyalty program free beverage: CHF -" + df.format(cheapestBeverage.getProduct().getProductPrice()));
            order.getLoyaltyStampProgram().setStamps(order.getLoyaltyStampProgram().getStamps() - 5);
            System.out.printf("|%75s | %-16s %-2s |%n", "", "Stamps left: ", order.getLoyaltyStampProgram().getStamps());
        }
    }

    private void checkIfEligibleForComboMealDeal(Order order) {
        long countBeverages = order.getOrderItemsList().stream()
                .filter(orderItem -> orderItem.getProduct().getProductType().equals(ProductType.BEVERAGE))
                .findAny().stream().count();

        long countSnacks = order.getOrderItemsList().stream()
                .filter(orderItem -> orderItem.getProduct().getProductType().equals(ProductType.SNACK))
                .findAny().stream().count();

        if (countBeverages > 0 && countSnacks > 0) {
            OrderItem cheapestExtras = order.getOrderItemsList().stream()
                    .filter(orderItem -> orderItem.getProductExtra() != null)
                    .min(Comparator.comparingDouble(product -> product.getProductExtra().getProductPrice()))
                    .orElse(null);
            if (cheapestExtras != null) {
                System.out.printf("|%64s | %-16s |%n", "", "Combo meal discount: CHF -" + df.format(cheapestExtras.getProductExtra().getProductPrice()));
                order.setTotalOrderValue(order.getTotalOrderValue() - cheapestExtras.getProductExtra().getProductPrice());
            }
        }
    }
}
