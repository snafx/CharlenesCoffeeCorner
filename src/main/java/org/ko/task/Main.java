package org.ko.task;

import org.ko.task.model.Product;
import org.ko.task.services.OrderServices;
import org.ko.task.warehouse.LoadProducts;

import java.util.List;

public class Main {

    public static final String WELCOME_MSG = "\n********************************************\n* Welcome in the Charlene's Coffee Corner! *\n********************************************\n\n";
    public static final String GOODBYE_MSG = "\n**********************************************\n*           Goodbye happy customer!          *\n**********************************************\n";

    public static void main(String[] args) {
        System.out.println(WELCOME_MSG);

        LoadProducts loadProducts = new LoadProducts();
        List<Product> availableProductList = loadProducts.stockUpCoffeeShopInventory();

        OrderServices orderServices = new OrderServices();
        orderServices.createOrder(availableProductList);

        System.out.println(GOODBYE_MSG);

    }
}
