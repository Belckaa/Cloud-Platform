package com.cloudplatform.infrastructure.messaging.consumer;

import com.cloudplatform.events.PaymentProcessedEvent;
import com.cloudplatform.infrastructure.messaging.idempotency.InMemoryProcessedEventStore;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final class PaymentAuditConsumer implements Consumer<PaymentProcessedEvent> {

    private final InMemoryProcessedEventStore processedStore;
    private final AtomicInteger auditedPayments = new AtomicInteger();

    public PaymentAuditConsumer(InMemoryProcessedEventStore processedStore) {
        this.processedStore = processedStore;
    }

    @Override
    public void accept(PaymentProcessedEvent event) {
        processedStore.executeOnce("PaymentAuditConsumer", event.eventId(), () -> {
            auditedPayments.incrementAndGet();

            System.out.println(
                    "[PAYMENT AUDIT] Payment processed: payment="
                            + event.paymentId()
                            + ", order=" + event.aggregateId()
                            + ", amount=" + event.amount()
            );
        });
    }

    public int auditedPaymentCount() {
        return auditedPayments.get();
    }
}
