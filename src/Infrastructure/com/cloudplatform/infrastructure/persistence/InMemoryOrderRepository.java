package com.cloudplatform.infrastructure.persistence;

import com.cloudplatform.domain.model.Order;
import com.cloudplatform.domain.repository.OrderRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public int count() {
        return orders.size();
    }
}