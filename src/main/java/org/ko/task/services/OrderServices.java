package org.ko.task.services;

import org.ko.task.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderServices {

    public void createOrder(List<Product> availableProductList) {
        System.out.println("\nPLACED ORDER RECEIPT:\n");
        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setOrderDate(getOrderDateAndTime());

        List<OrderItem> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderItem(1, availableProductList.get(2), availableProductList.get(5)));
        orderedProducts.add(new OrderItem(1, availableProductList.get(0), availableProductList.get(7)));
        orderedProducts.add(new OrderItem(1, availableProductList.get(4), null));
        orderedProducts.add(new OrderItem(1, availableProductList.get(3), null));
        order1.setOrderItemsList(orderedProducts);

        LoyaltyStampProgram loyaltyStampProgram = new LoyaltyStampProgram();
        loyaltyStampProgram.setCustomerId("1");
        orderedProducts.forEach(product -> {
            boolean equals = product.getProduct().getProductType().equals(ProductType.BEVERAGE);
            if (equals) {
                loyaltyStampProgram.setStamps(loyaltyStampProgram.getStamps() + product.getQuantity());
            }
        });
        order1.setLoyaltyStampProgram(loyaltyStampProgram);


        order1.printOrder();
    }

    private String getOrderDateAndTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
