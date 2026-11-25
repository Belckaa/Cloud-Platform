package com.cloudplatform.infrastructure.messaging.idempotency;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class InMemoryProcessedEventStore {

    private final Set<String> processedEvents = new HashSet<>();

    public synchronized boolean executeOnce(
            String consumerName,
            UUID eventId,
            Runnable action
    ) {
        String key = consumerName + ":" + eventId;

        if (processedEvents.contains(key)) {
            System.out.println(
                    "[IDEMPOTENCY] DUPLICATE SKIPPED: consumer="
                            + consumerName + ", eventId=" + eventId
            );
            return false;
        }

        action.run();
        processedEvents.add(key);

        System.out.println(
                "[IDEMPOTENCY] MARKED PROCESSED: consumer="
                        + consumerName + ", eventId=" + eventId
        );

        return true;
    }

    public synchronized int processedCount() {
        return processedEvents.size();
    }
}
