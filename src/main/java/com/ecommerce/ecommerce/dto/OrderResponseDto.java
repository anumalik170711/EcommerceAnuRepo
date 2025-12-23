package com.ecommerce.ecommerce.dto;

import java.util.List;

public record OrderResponseDto(Long orderId,
                               double totalAmount,
                               String status,
                               List<OrderItemResponseDto> items) {
}
