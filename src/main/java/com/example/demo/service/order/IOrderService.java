package com.example.demo.service.order;

import com.example.demo.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
