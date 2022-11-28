package org.ko.task.warehouse;

import org.ko.task.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoadProducts {

    private final List<Product> availableProductList = new ArrayList<>();

    public void stockUpCoffeeShopInventory() {
        addProducts();
        System.out.println("Charlene's Coffee Corner MENU:");
        availableProductList.forEach(System.out::println);
        System.out.println("\nPLACED ORDER RECEIPT:\n");
        createOrder();

    }

    private void createOrder() {
        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setOrderDate(getOrderDateAndTime());

        List<OrderItem> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderItem(1, availableProductList.get(2), availableProductList.get(5)));
        orderedProducts.add(new OrderItem(1, availableProductList.get(0), availableProductList.get(7)));
        orderedProducts.add(new OrderItem(1, availableProductList.get(0), availableProductList.get(7)));
        orderedProducts.add(new OrderItem(1, availableProductList.get(4), null));
        orderedProducts.add(new OrderItem(1, availableProductList.get(3), null));
        orderedProducts.add(new OrderItem(1, availableProductList.get(3), null));
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

    private void addProducts() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setProductName("Coffee, small");
        product1.setProductPrice(2.50);
        product1.setProductType(ProductType.BEVERAGE);
        availableProductList.add(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setProductName("Coffee, medium");
        product2.setProductPrice(3.00);
        product2.setProductType(ProductType.BEVERAGE);
        availableProductList.add(product2);

        Product product3 = new Product();
        product3.setId(3);
        product3.setProductName("Coffee, large");
        product3.setProductPrice(3.50);
        product3.setProductType(ProductType.BEVERAGE);
        availableProductList.add(product3);

        Product product4 = new Product();
        product4.setId(4);
        product4.setProductName("Freshly squeezed orange juice (0.25l)");
        product4.setProductPrice(3.95);
        product4.setProductType(ProductType.BEVERAGE);
        availableProductList.add(product4);

        Product product5 = new Product();
        product5.setId(5);
        product5.setProductName("Bacon Roll");
        product5.setProductPrice(4.50);
        product5.setProductType(ProductType.SNACK);
        availableProductList.add(product5);

        Product product6 = new Product();
        product6.setId(6);
        product6.setProductName("Extra milk");
        product6.setProductPrice(0.30);
        product6.setProductType(ProductType.EXTRAS);
        availableProductList.add(product6);

        Product product7 = new Product();
        product7.setId(7);
        product7.setProductName("Foamed milk");
        product7.setProductPrice(0.50);
        product7.setProductType(ProductType.EXTRAS);
        availableProductList.add(product7);

        Product product8 = new Product();
        product8.setId(8);
        product8.setProductName("Special roast coffee");
        product8.setProductPrice(0.90);
        product8.setProductType(ProductType.EXTRAS);
        availableProductList.add(product8);

    }

    private String getOrderDateAndTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
