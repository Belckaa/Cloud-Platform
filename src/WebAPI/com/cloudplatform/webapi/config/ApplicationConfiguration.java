package com.cloudplatform.webapi.config;

import com.cloudplatform.application.port.IEventPublisher;
import com.cloudplatform.application.port.InventoryAvailabilityPort;
import com.cloudplatform.application.usecase.CreateOrderUseCase;
import com.cloudplatform.domain.repository.OrderRepository;
import com.cloudplatform.infrastructure.inventory.InMemoryInventoryAvailabilityAdapter;
import com.cloudplatform.infrastructure.messaging.InMemoryEventBus;
import com.cloudplatform.infrastructure.messaging.consumer.OrderNotificationConsumer;
import com.cloudplatform.infrastructure.messaging.consumer.PaymentAuditConsumer;
import com.cloudplatform.infrastructure.messaging.consumer.StockReservationConsumer;
import com.cloudplatform.infrastructure.messaging.idempotency.InMemoryProcessedEventStore;
import com.cloudplatform.infrastructure.persistence.InMemoryOrderRepository;
import com.cloudplatform.webapi.controller.CreateOrderController;
import com.cloudplatform.events.OrderCreatedEvent;
import com.cloudplatform.events.PaymentProcessedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    public InventoryAvailabilityPort inventoryAvailabilityPort() {
        return new InMemoryInventoryAvailabilityAdapter();
    }

    @Bean(destroyMethod = "close")
    public InMemoryEventBus inMemoryEventBus() {
        return new InMemoryEventBus();
    }

    @Bean
    public IEventPublisher eventPublisher(InMemoryEventBus eventBus) {
        return eventBus;
    }

    @Bean
    public InMemoryProcessedEventStore processedEventStore() {
        return new InMemoryProcessedEventStore();
    }

    @Bean
    public StockReservationConsumer stockReservationConsumer(
            InMemoryEventBus eventBus,
            InMemoryProcessedEventStore store
    ) {
        StockReservationConsumer consumer = new StockReservationConsumer(store);
        eventBus.subscribe(OrderCreatedEvent.class, consumer);
        return consumer;
    }

    @Bean
    public OrderNotificationConsumer orderNotificationConsumer(
            InMemoryEventBus eventBus,
            InMemoryProcessedEventStore store
    ) {
        OrderNotificationConsumer consumer = new OrderNotificationConsumer(store);
        eventBus.subscribe(OrderCreatedEvent.class, consumer);
        return consumer;
    }

    @Bean
    public PaymentAuditConsumer paymentAuditConsumer(
            InMemoryEventBus eventBus,
            InMemoryProcessedEventStore store
    ) {
        PaymentAuditConsumer consumer = new PaymentAuditConsumer(store);
        eventBus.subscribe(PaymentProcessedEvent.class, consumer);
        return consumer;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CreateOrderUseCase createOrderUseCase(
            OrderRepository orderRepository,
            InventoryAvailabilityPort inventoryAvailabilityPort,
            IEventPublisher eventPublisher
    ) {
        return new CreateOrderUseCase(
                orderRepository,
                inventoryAvailabilityPort,
                eventPublisher
        );
    }

    @Bean
    @RequestScope
    public CreateOrderController createOrderController(CreateOrderUseCase useCase) {
        return new CreateOrderController(useCase);
    }
}
