package com.cloudplatform.infrastructure.messaging;

import com.cloudplatform.application.port.IEventPublisher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public final class InMemoryEventBus implements IEventPublisher, AutoCloseable {

    private final ExecutorService executor;
    private final Map<Class<?>, CopyOnWriteArrayList<Consumer<Object>>> subscribers =
            new ConcurrentHashMap<>();

    public InMemoryEventBus() {
        this.executor = Executors.newFixedThreadPool(4);
    }

    public <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
        Consumer<Object> adapter = event -> handler.accept(eventType.cast(event));

        subscribers.computeIfAbsent(
                eventType,
                ignored -> new CopyOnWriteArrayList<>()
        ).add(adapter);

        System.out.println("[BUS] SUBSCRIBED: " + eventType.getSimpleName());
    }

    @Override
    public void publish(Object event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        List<Consumer<Object>> handlers = subscribers.getOrDefault(
                event.getClass(),
                new CopyOnWriteArrayList<>()
        );

        System.out.println(
                "[BUS] PUBLISHED: " + event.getClass().getSimpleName()
                        + ", subscribers=" + handlers.size()
        );

        for (Consumer<Object> handler : handlers) {
            executor.submit(() -> {
                try {
                    handler.accept(event);
                } catch (Exception exception) {
                    System.err.println(
                            "[BUS] CONSUMER FAILURE: "
                                    + exception.getClass().getSimpleName()
                                    + " - " + exception.getMessage()
                    );
                }
            });
        }
    }

    @Override
    public void close() {
        executor.shutdown();

        try {
            if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException exception) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
