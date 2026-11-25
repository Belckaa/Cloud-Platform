package com.cloudplatform.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId,
        UUID customerId,
        BigDecimal totalAmount,
        int itemCount
) {
    public OrderCreatedEvent {
        Objects.requireNonNull(eventId, "eventId");
        Objects.requireNonNull(occurredAt, "occurredAt");
        Objects.requireNonNull(aggregateId, "aggregateId");
        Objects.requireNonNull(customerId, "customerId");
        Objects.requireNonNull(totalAmount, "totalAmount");

        if (itemCount < 1) {
            throw new IllegalArgumentException("itemCount must be positive");
        }
    }
}