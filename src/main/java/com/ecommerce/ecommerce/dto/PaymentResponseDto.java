package com.ecommerce.ecommerce.dto;

public record PaymentResponseDto(Long paymentId,
                                 Long orderId,
                                 double amount,
                                 String status,
                                 String transactionId) {
}
