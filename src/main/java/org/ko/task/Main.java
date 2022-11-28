package org.ko.task;

import org.ko.task.model.Product;
import org.ko.task.services.OrderServices;
import org.ko.task.warehouse.LoadProducts;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        System.out.println("Starting....");

        LoadProducts loadProducts = new LoadProducts();
        List<Product> availableProductList = loadProducts.stockUpCoffeeShopInventory();

        OrderServices orderServices = new OrderServices();
        orderServices.createOrder(availableProductList);

    }
}
