package com.cloudplatform.infrastructure.messaging.consumer;

import com.cloudplatform.events.OrderCreatedEvent;
import com.cloudplatform.infrastructure.messaging.idempotency.InMemoryProcessedEventStore;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final class StockReservationConsumer implements Consumer<OrderCreatedEvent> {

    private final InMemoryProcessedEventStore processedStore;
    private final AtomicInteger reservations = new AtomicInteger();

    public StockReservationConsumer(InMemoryProcessedEventStore processedStore) {
        this.processedStore = processedStore;
    }

    @Override
    public void accept(OrderCreatedEvent event) {
        processedStore.executeOnce("StockReservationConsumer", event.eventId(), () -> {
            reservations.incrementAndGet();

            System.out.println(
                    "[STOCK] Reservation processed for order="
                            + event.aggregateId()
                            + ", items=" + event.itemCount()
            );
        });
    }

    public int reservationCount() {
        return reservations.get();
    }
}
