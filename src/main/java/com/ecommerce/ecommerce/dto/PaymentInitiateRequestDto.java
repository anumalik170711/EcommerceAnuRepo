package com.ecommerce.ecommerce.dto;

public record PaymentInitiateRequestDto(Long orderId,
                                        String method) {
}
