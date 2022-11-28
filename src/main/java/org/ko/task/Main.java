package org.ko.task;

import org.ko.task.warehouse.LoadProducts;

public class Main {


    public static void main(String[] args) {

        System.out.println("Starting....");
        LoadProducts loadProducts = new LoadProducts();
        loadProducts.stockUpCoffeeShopInventory();

    }
}
