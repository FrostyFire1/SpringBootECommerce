package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.payload.APIResponse;
import com.frosty.SpringBootECommerce.payload.CartDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity) {
        return new ResponseEntity<>(cartService.addProductToCart(productId, quantity), HttpStatus.CREATED);
    }

    @PutMapping("/carts/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateProductInCart(@PathVariable Long productId, @PathVariable String operation) {
        return ResponseEntity.ok(cartService.updateProductQuantityInCart(productId, operation));
    }

    @GetMapping("/carts")
    public ResponseEntity<ContentResponse<CartDTO>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getUserCart() {
        return ResponseEntity.ok(cartService.getUserCartDTO());
    }

    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<APIResponse> deleteItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.deleteItemFromCart(cartId, productId));
    }
}
