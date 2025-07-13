package com.example.demo.service.cart;


import java.math.BigDecimal;

import com.example.demo.model.Cart;
import com.example.demo.model.User;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);



    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
