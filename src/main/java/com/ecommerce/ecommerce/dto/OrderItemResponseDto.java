package com.ecommerce.ecommerce.dto;

public record OrderItemResponseDto(Long productId,
                                   String productName,
                                   int quantity,
                                   double price) {
}
