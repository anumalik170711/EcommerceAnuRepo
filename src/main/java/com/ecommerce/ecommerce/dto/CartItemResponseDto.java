package com.ecommerce.ecommerce.dto;

public record CartItemResponseDto(
        Long cartItemId,
        Long productId,
        String productName,
        double price,
        int quantity
) {
}
