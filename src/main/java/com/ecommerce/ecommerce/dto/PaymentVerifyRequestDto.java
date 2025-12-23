package com.ecommerce.ecommerce.dto;

public record PaymentVerifyRequestDto(Long paymentId,
                                      String transactionId,
                                      boolean success) {
}
