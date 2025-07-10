package com.example.demo.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        //set the user here when user entity created
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){
        return orderItemList.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    //reducing ordered products from inventory
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice());
                }).toList();
    }

    //ORDER SERVICE IS COMPLICATED SO WILL REQUIRE A FEW HELPER METHODS

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
