package com.ecommerce.ecommerce.dto;


public record ProductResponseDto(
        Long id,
        String name,
        String description,
        Double price,
        int stockPresent,
        String categoryName
) {}
