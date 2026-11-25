package com.cloudplatform.application.usecase;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        List<OrderLine> items,
        ShippingAddress shippingAddress
) {
    public CreateOrderCommand {
        items = items == null ? null : List.copyOf(items);
    }

    public record OrderLine(UUID productId, int quantity, BigDecimal unitPrice) {}

    public record ShippingAddress(
            String country,
            String city,
            String street,
            String building,
            String postalCode
    ) {}
}