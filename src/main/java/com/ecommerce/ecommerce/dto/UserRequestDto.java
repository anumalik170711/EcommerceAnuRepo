package com.ecommerce.ecommerce.dto;

public record UserRequestDto(
        String name,
        String email,
        String password,
        String phoneNumber
) {
}
