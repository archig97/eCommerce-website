package com.example.demo.service.cart;

import com.example.demo.model.CartItem;

public interface ICartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    //not exactly required but thia code is used multiple times so better just keep calling the function
    CartItem getCartItem(Long cartId, Long productId);
}
