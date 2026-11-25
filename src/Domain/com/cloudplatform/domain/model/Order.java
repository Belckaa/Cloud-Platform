package com.cloudplatform.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class Order {
    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> items;
    private final Address shippingAddress;
    private final BigDecimal totalAmount;
    private final OrderStatus status;
    private final Instant createdAt;

    private Order(
            UUID id,
            UUID customerId,
            List<OrderItem> items,
            Address shippingAddress,
            BigDecimal totalAmount,
            OrderStatus status,
            Instant createdAt
    ) {
        this.id = id;
        this.customerId = customerId;
        this.items = List.copyOf(items);
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Order create(
            UUID customerId,
            List<OrderItem> items,
            Address shippingAddress
    ) {
        if (customerId == null) {
            throw new IllegalArgumentException("Ідентифікатор клієнта обов'язковий.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Замовлення повинно містити хоча б один товар.");
        }
        if (shippingAddress == null) {
            throw new IllegalArgumentException("Адреса доставки обов'язкова.");
        }

        BigDecimal total = items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Order(
                UUID.randomUUID(),
                customerId,
                items,
                shippingAddress,
                total,
                OrderStatus.PENDING,
                Instant.now()
        );
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public Address getShippingAddress() { return shippingAddress; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}