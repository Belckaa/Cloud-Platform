package com.cloudplatform.domain.model;

public record Address(
        String country,
        String city,
        String street,
        String building,
        String postalCode
) {
    public Address {
        if (isBlank(country) || isBlank(city) || isBlank(street)
                || isBlank(building) || isBlank(postalCode)) {
            throw new IllegalArgumentException("Усі поля адреси є обов'язковими.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}