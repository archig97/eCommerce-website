package com.example.demo.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.response.APIResponse;
import com.example.demo.service.cart.ICartItemService;
import com.example.demo.service.cart.ICartService;
import com.example.demo.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try{
            //check if user has a cart
            //hardcoding for now, Long value so add an L
            User user = userService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(user);




            cartItemService.addItemToCart(cart.getId(), productId, quantity);
        return ResponseEntity.ok(new APIResponse("Added new item successfully",null));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<APIResponse> removeItemsFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        try{
            cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new APIResponse("Item removed successfully",null));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<APIResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity){
        try{
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new APIResponse("Item updated successfully",null));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }
}
