package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.CartItemRequestDto;
import com.ecommerce.ecommerce.dto.CartResponseDto;
import com.ecommerce.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {


    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }


    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addProductToCart(@RequestBody CartItemRequestDto dto) throws Exception {
        return ResponseEntity.ok(service.addProductToCart(dto));
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartResponseDto> updateQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) throws Exception {
        return ResponseEntity.ok(service.updateQuantity(cartItemId, quantity));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartResponseDto> removeItem(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(service.removeItem(cartItemId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(service.getCartByUser(userId));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Boolean> clearCart(@PathVariable Long userId) {
        return ResponseEntity.ok(service.clearCart(userId));
    }
}
