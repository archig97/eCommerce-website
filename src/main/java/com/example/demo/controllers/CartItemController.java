package com.example.demo.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.response.APIResponse;
import com.example.demo.service.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;

    @PostMapping("/item/add")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try{
            cartItemService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok(new APIResponse("Added new item successfully",null));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }

    }
}
