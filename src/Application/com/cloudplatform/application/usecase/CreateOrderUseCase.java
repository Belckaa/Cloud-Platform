package com.cloudplatform.application.usecase;

import com.cloudplatform.application.port.IEventPublisher;
import com.cloudplatform.application.port.InventoryAvailabilityPort;
import com.cloudplatform.domain.model.Address;
import com.cloudplatform.domain.model.Order;
import com.cloudplatform.domain.model.OrderItem;
import com.cloudplatform.domain.repository.OrderRepository;
import com.cloudplatform.events.OrderCreatedEvent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final InventoryAvailabilityPort inventoryPort;
    private final IEventPublisher eventPublisher;

    public CreateOrderUseCase(
            OrderRepository orderRepository,
            InventoryAvailabilityPort inventoryPort,
            IEventPublisher eventPublisher
    ) {
        this.orderRepository = orderRepository;
        this.inventoryPort = inventoryPort;
        this.eventPublisher = eventPublisher;
    }

    public Order execute(CreateOrderCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Create order command is required.");
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

        Order order = Order.create(command.customerId(), items, address);

        for (OrderItem item : order.getItems()) {
            if (!inventoryPort.isAvailable(item.productId(), item.quantity())) {
                throw new InsufficientInventoryException(
                        "Insufficient inventory for product: " + item.productId()
                );
            }
        }

        Order savedOrder = orderRepository.save(order);

        eventPublisher.publish(new OrderCreatedEvent(
                UUID.randomUUID(),
                Instant.now(),
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount(),
                savedOrder.getItems().size()
        ));

        return savedOrder;
    }
}
