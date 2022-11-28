package org.ko.task.warehouse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ko.task.model.Product;
import org.ko.task.model.ProductType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadProductsTest {

    private static List<Product> coffeeShopInventory = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        LoadProducts loadProducts = new LoadProducts();
        coffeeShopInventory = loadProducts.stockUpCoffeeShopInventory();
    }

    @Test
    void createProductTest() {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setProductName("Coffee, small");
        newProduct.setProductPrice(2.50);
        newProduct.setProductType(ProductType.BEVERAGE);


        assertEquals(newProduct.getId(), coffeeShopInventory.get(0).getId());
        assertEquals(newProduct.getProductName(), coffeeShopInventory.get(0).getProductName());
        assertEquals(newProduct.getProductPrice(), coffeeShopInventory.get(0).getProductPrice());
        assertEquals(newProduct.getProductType(), coffeeShopInventory.get(0).getProductType());

    }

    @Test
    void checkInventorySize() {
        assertEquals(8, coffeeShopInventory.size());
    }
}
