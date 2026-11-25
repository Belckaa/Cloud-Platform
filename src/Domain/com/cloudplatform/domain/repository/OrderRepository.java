package com.cloudplatform.domain.repository;

import com.cloudplatform.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
}