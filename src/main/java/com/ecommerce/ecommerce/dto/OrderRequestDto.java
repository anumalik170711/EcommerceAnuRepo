package com.ecommerce.ecommerce.dto;

public record OrderRequestDto(Long userId,
                              Long addressId,
                              String paymentMethod) {
}
