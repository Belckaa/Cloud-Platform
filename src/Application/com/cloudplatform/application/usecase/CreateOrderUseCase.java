package com.cloudplatform.application.usecase;

import com.cloudplatform.application.port.InventoryAvailabilityPort;
import com.cloudplatform.domain.model.Address;
import com.cloudplatform.domain.model.Order;
import com.cloudplatform.domain.model.OrderItem;
import com.cloudplatform.domain.repository.OrderRepository;

import java.util.List;

public final class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final InventoryAvailabilityPort inventoryPort;

    public CreateOrderUseCase(
            OrderRepository orderRepository,
            InventoryAvailabilityPort inventoryPort
    ) {
        this.orderRepository = orderRepository;
        this.inventoryPort = inventoryPort;
    }

    public Order execute(CreateOrderCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Команда створення замовлення обов'язкова.");
        }

        List<OrderItem> items = command.items() == null
                ? List.of()
                : command.items().stream()
                    .map(item -> new OrderItem(
                            item.productId(),
                            item.quantity(),
                            item.unitPrice()
                    ))
                    .toList();

        CreateOrderCommand.ShippingAddress sourceAddress = command.shippingAddress();
        Address address = sourceAddress == null ? null : new Address(
                sourceAddress.country(),
                sourceAddress.city(),
                sourceAddress.street(),
                sourceAddress.building(),
                sourceAddress.postalCode()
        );

        // Доменні правила перевіряються до звернення до зовнішніх портів.
        Order order = Order.create(command.customerId(), items, address);

        for (OrderItem item : order.getItems()) {
            if (!inventoryPort.isAvailable(item.productId(), item.quantity())) {
                throw new InsufficientInventoryException(
                        "Недостатньо товару на складі: " + item.productId()
                );
            }
        }

        return orderRepository.save(order);
    }
}