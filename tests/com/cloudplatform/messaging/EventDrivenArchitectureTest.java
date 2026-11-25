package com.cloudplatform.messaging;

import com.cloudplatform.events.OrderCreatedEvent;
import com.cloudplatform.events.PaymentProcessedEvent;
import com.cloudplatform.infrastructure.messaging.InMemoryEventBus;
import com.cloudplatform.infrastructure.messaging.consumer.OrderNotificationConsumer;
import com.cloudplatform.infrastructure.messaging.consumer.PaymentAuditConsumer;
import com.cloudplatform.infrastructure.messaging.consumer.StockReservationConsumer;
import com.cloudplatform.infrastructure.messaging.idempotency.InMemoryProcessedEventStore;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class EventDrivenArchitectureTest {

    @Test
    void duplicateOrderEventIsProcessedOnceAndConsumerFailureIsIsolated()
            throws InterruptedException {

        try (InMemoryEventBus bus = new InMemoryEventBus()) {
            InMemoryProcessedEventStore store = new InMemoryProcessedEventStore();

            StockReservationConsumer stockConsumer =
                    new StockReservationConsumer(store);

            OrderNotificationConsumer notificationConsumer =
                    new OrderNotificationConsumer(store);

            AtomicInteger failingConsumerAttempts = new AtomicInteger();
            CountDownLatch completedHandlers = new CountDownLatch(6);

            bus.subscribe(OrderCreatedEvent.class, event -> {
                try {
                    stockConsumer.accept(event);
                } finally {
                    completedHandlers.countDown();
                }
            });

            bus.subscribe(OrderCreatedEvent.class, event -> {
                try {
                    notificationConsumer.accept(event);
                } finally {
                    completedHandlers.countDown();
                }
            });

            bus.subscribe(OrderCreatedEvent.class, event -> {
                try {
                    failingConsumerAttempts.incrementAndGet();
                    throw new IllegalStateException("Simulated consumer failure");
                } finally {
                    completedHandlers.countDown();
                }
            });

            UUID orderId = UUID.randomUUID();

            OrderCreatedEvent event = new OrderCreatedEvent(
                    UUID.randomUUID(),
                    Instant.now(),
                    orderId,
                    UUID.randomUUID(),
                    new BigDecimal("300.00"),
                    2
            );

            System.out.println("[DEMO] Publishing the same event twice: " + event.eventId());

            bus.publish(event);
            bus.publish(event);

            assertTrue(
                    completedHandlers.await(5, TimeUnit.SECONDS),
                    "All asynchronous handlers should complete"
            );

            assertEquals(1, stockConsumer.reservationCount());
            assertEquals(1, notificationConsumer.notificationCount());
            assertEquals(2, failingConsumerAttempts.get());

            System.out.println("[DEMO] Duplicate delivery verified.");
            System.out.println("[DEMO] Stock reservations: "
                    + stockConsumer.reservationCount());
            System.out.println("[DEMO] Notifications: "
                    + notificationConsumer.notificationCount());
            System.out.println("[DEMO] Failed consumer attempts: "
                    + failingConsumerAttempts.get());
            System.out.println("[DEMO] Healthy consumers completed despite failure.");
        }
    }

    @Test
    void paymentProcessedEventIsDeliveredToItsIndependentConsumer()
            throws InterruptedException {

        try (InMemoryEventBus bus = new InMemoryEventBus()) {
            InMemoryProcessedEventStore store = new InMemoryProcessedEventStore();
            PaymentAuditConsumer consumer = new PaymentAuditConsumer(store);
            CountDownLatch completed = new CountDownLatch(1);

            bus.subscribe(PaymentProcessedEvent.class, event -> {
                try {
                    consumer.accept(event);
                } finally {
                    completed.countDown();
                }
            });

            PaymentProcessedEvent event = new PaymentProcessedEvent(
                    UUID.randomUUID(),
                    Instant.now(),
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    new BigDecimal("300.00"),
                    "CARD"
            );

            bus.publish(event);

            assertTrue(completed.await(5, TimeUnit.SECONDS));
            assertEquals(1, consumer.auditedPaymentCount());
        }
    }
}
