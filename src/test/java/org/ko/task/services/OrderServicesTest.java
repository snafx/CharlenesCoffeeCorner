package org.ko.task.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ko.task.model.Order;
import org.ko.task.model.OrderItem;
import org.ko.task.model.Product;
import org.ko.task.warehouse.LoadProducts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServicesTest {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static List<Product> coffeeShopInventory = new ArrayList<>();
    private static List<OrderItem> defaultOrderItemList = new ArrayList<>();
    private static OrderServices orderServices = new OrderServices();

    @BeforeAll
    public static void setUp() {
        LoadProducts loadProducts = new LoadProducts();
        orderServices = new OrderServices();
        coffeeShopInventory = loadProducts.stockUpCoffeeShopInventory();
        defaultOrderItemList = listOfProductsToOrder();
    }

    public static List<OrderItem> listOfProductsToOrder() {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(1, coffeeShopInventory.get(2), coffeeShopInventory.get(5)));
        orderItemList.add(new OrderItem(1, coffeeShopInventory.get(0), coffeeShopInventory.get(7)));
        orderItemList.add(new OrderItem(1, coffeeShopInventory.get(4), null));
        orderItemList.add(new OrderItem(1, coffeeShopInventory.get(3), null));

        return orderItemList;
    }

    @Test
    void createOrderTest() {
        OrderServices orderServices = new OrderServices();
        Order order = orderServices.createOrder(defaultOrderItemList);

        assertEquals("15.35", df.format(order.getTotalOrderValue()));
        assertEquals(4, order.getOrderItemsList().size());
        assertEquals(3, order.getLoyaltyStampProgram().getStamps());
    }

    @Test
    void checkIfEligibleForFreeBeverageTest() {
        Order order= new Order();
        order.setOrderId(2);
        order.setOrderDate(orderServices.getOrderDateAndTime());
        List<OrderItem> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderItem(1, coffeeShopInventory.get(1), coffeeShopInventory.get(7)));
        orderedProducts.add(new OrderItem(2, coffeeShopInventory.get(3), null));
        orderedProducts.add(new OrderItem(3, coffeeShopInventory.get(0), null));
        orderedProducts.add(new OrderItem(3, coffeeShopInventory.get(4), null));
        order.setOrderItemsList(orderedProducts);

        orderServices.updateLoyaltyStampProgram(order, orderedProducts);

        assertEquals(4, order.getOrderItemsList().size());
        assertEquals(6, order.getLoyaltyStampProgram().getStamps());

        orderedProducts.add(new OrderItem(1, coffeeShopInventory.get(2), coffeeShopInventory.get(5)));
        orderedProducts.add(new OrderItem(1, coffeeShopInventory.get(0), coffeeShopInventory.get(7)));
        orderedProducts.add(new OrderItem(1, coffeeShopInventory.get(4), null));
        orderedProducts.add(new OrderItem(1, coffeeShopInventory.get(3), null));

        orderServices.updateLoyaltyStampProgram(order, orderedProducts);
        assertEquals(8, order.getOrderItemsList().size());
        assertEquals(9, order.getLoyaltyStampProgram().getStamps());
    }

    @Test
    void createAndAddMoreProductsToOrderTest() {
        Order order = orderServices.createOrder(defaultOrderItemList);

        assertEquals("15.35", df.format(order.getTotalOrderValue()));
        assertEquals(4, order.getOrderItemsList().size());
        assertEquals(3, order.getLoyaltyStampProgram().getStamps());

        orderServices.addProductToOrder(new OrderItem(2, coffeeShopInventory.get(1), coffeeShopInventory.get(7)));
        orderServices.addProductToOrder(new OrderItem(2, coffeeShopInventory.get(3), null));
        orderServices.addProductToOrder(new OrderItem(3, coffeeShopInventory.get(0), null));

        assertEquals(7, order.getOrderItemsList().size());
        assertEquals(3, order.getLoyaltyStampProgram().getStamps());

        orderServices.updateLoyaltyStampProgram(order, order.getOrderItemsList());
        assertEquals(10, order.getLoyaltyStampProgram().getStamps());

        orderServices.calculateTotalBillCost(order);
        assertEquals(5, order.getLoyaltyStampProgram().getStamps());

    }
}
