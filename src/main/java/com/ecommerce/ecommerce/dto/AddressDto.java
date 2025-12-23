package com.ecommerce.ecommerce.dto;

public record AddressDto(
        Long id,
        String street,
        String city,
        String state,
        String zipcode,
        String country
) {}


