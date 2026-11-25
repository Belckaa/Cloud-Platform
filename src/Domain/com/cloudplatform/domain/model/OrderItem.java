package com.cloudplatform.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(UUID productId, int quantity, BigDecimal unitPrice) {
    public OrderItem {
        if (productId == null) {
            throw new IllegalArgumentException("Ідентифікатор товару обов'язковий.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість товару має бути більшою за нуль.");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Ціна товару має бути більшою за нуль.");
        }
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}