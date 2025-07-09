package com.example.demo.service.cart;


import java.math.BigDecimal;

import com.example.demo.model.Cart;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
