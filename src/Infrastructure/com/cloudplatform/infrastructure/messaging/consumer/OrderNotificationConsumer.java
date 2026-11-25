package com.cloudplatform.infrastructure.messaging.consumer;

import com.cloudplatform.events.OrderCreatedEvent;
import com.cloudplatform.infrastructure.messaging.idempotency.InMemoryProcessedEventStore;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final class OrderNotificationConsumer implements Consumer<OrderCreatedEvent> {

    private final InMemoryProcessedEventStore processedStore;
    private final AtomicInteger notifications = new AtomicInteger();

    public OrderNotificationConsumer(InMemoryProcessedEventStore processedStore) {
        this.processedStore = processedStore;
    }

    @Override
    public void accept(OrderCreatedEvent event) {
        processedStore.executeOnce("OrderNotificationConsumer", event.eventId(), () -> {
            notifications.incrementAndGet();

            System.out.println(
                    "[NOTIFICATION] Order created notification sent: order="
                            + event.aggregateId()
                            + ", customer=" + event.customerId()
            );
        });
    }

    public int notificationCount() {
        return notifications.get();
    }
}
