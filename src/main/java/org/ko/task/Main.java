package org.ko.task;

import org.ko.task.model.OrderItem;
import org.ko.task.model.Product;
import org.ko.task.services.OrderServices;
import org.ko.task.warehouse.LoadProducts;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String WELCOME_MSG = "\n********************************************\n* Welcome in the Charlene's Coffee Corner! *\n********************************************\n\n";
    private static final String GOODBYE_MSG = "\n**********************************************\n*           Goodbye happy customer!          *\n**********************************************\n";
    private static List<Product> availableProductList = new ArrayList<>();

    public static void main(String[] args) {
        openCoffeeShop();
    }

    private static void openCoffeeShop() {
        LoadProducts loadProducts = new LoadProducts();
        OrderServices orderServices = new OrderServices();
        System.out.println(WELCOME_MSG);

        availableProductList = loadProducts.stockUpCoffeeShopInventory();

        List<OrderItem> smallOrder = loadProducts.listOfProductsToOrder(availableProductList);
        List<OrderItem> bigOrder = loadProducts.listOfProductsToOrder(availableProductList);
        List<OrderItem> veryThirstyAndHungryOrder = loadProducts.addMoreOrderItems(availableProductList);

        System.out.println("\nSMALL ORDER:");
        orderServices.createOrder(smallOrder);

        System.out.println("\nBIG ORDER:");
        orderServices.createOrder(bigOrder);

        System.out.println("\nVERY THIRSTY AND HUNGRY ORDER:");
        orderServices.createOrder(veryThirstyAndHungryOrder);

        System.out.println(GOODBYE_MSG);
    }

}
