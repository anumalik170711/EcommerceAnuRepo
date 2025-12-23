package com.ecommerce.ecommerce.dto;

public record CartItemRequestDto(   Long userId,
                                    Long productId,
                                    int quantity) {
}
