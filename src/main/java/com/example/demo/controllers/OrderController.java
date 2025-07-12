package com.example.demo.controllers;

import java.util.List;

import com.example.demo.dto.OrderDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.response.APIResponse;
import com.example.demo.service.order.IOrderService;
import com.example.demo.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    //create order for the user
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<APIResponse> createOrder(@RequestParam Long userId) {
        try{
            Order order = orderService.placeOrder(userId);
        return ResponseEntity.ok(new APIResponse("Order created successfully!", order));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error while creating order!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<APIResponse> getOrderById(@PathVariable Long orderId){
        try{
            OrderDTO order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new APIResponse("Order retrieved successfully!", order));

        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Order not found!", e.getMessage()));
        }

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId){
        try{
            //user may have more than one order in database - so we will need a list
            List<OrderDTO> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new APIResponse("Orders retrieved successfully!", orders));

        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Order not found!", e.getMessage()));
        }

    }





}
