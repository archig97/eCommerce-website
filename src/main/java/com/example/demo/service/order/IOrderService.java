package com.example.demo.service.order;

import java.util.List;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);

    //get list of orders for a user
    List<OrderDTO> getUserOrders(Long userId);
}
