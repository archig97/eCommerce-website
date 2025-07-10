package com.example.demo.service.order;

import java.util.List;

import com.example.demo.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    //get list of orders for a user
    List<Order> getUserOrders(Long userId);
}
