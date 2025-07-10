package com.example.demo.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.math.BigDecimal;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.response.APIResponse;
import com.example.demo.service.cart.ICartService;
import com.example.demo.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<APIResponse> getCart(@PathVariable Long cartId){
        try{
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new APIResponse("Success",cart));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }


    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<APIResponse> clearCart(@PathVariable Long cartId){
        try{
            cartService.clearCart(cartId);
        return ResponseEntity.ok(new APIResponse("Cleared cart successfully",null));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Item not found!",null));
        }

    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long cartId){
        try{
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new APIResponse("Total Price : ",totalPrice));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Item not found!",null));
        }
    }
}
