package com.cloudplatform.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record PaymentProcessedEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId,
        UUID paymentId,
        BigDecimal amount,
        String paymentMethod
) {
    public PaymentProcessedEvent {
        Objects.requireNonNull(eventId, "eventId");
        Objects.requireNonNull(occurredAt, "occurredAt");
        Objects.requireNonNull(aggregateId, "aggregateId");
        Objects.requireNonNull(paymentId, "paymentId");
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(paymentMethod, "paymentMethod");

        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }
}
