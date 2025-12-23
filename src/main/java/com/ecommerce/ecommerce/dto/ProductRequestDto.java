package com.ecommerce.ecommerce.dto;

import com.ecommerce.ecommerce.entity.CategoryEntity;

public record ProductRequestDto(String name, String description, Double price , int stockPresent , Long category) {
}
