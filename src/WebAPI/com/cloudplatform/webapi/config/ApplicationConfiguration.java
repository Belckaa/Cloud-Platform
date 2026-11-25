package com.cloudplatform.webapi.config;

import com.cloudplatform.application.port.InventoryAvailabilityPort;
import com.cloudplatform.application.usecase.CreateOrderUseCase;
import com.cloudplatform.domain.repository.OrderRepository;
import com.cloudplatform.infrastructure.inventory.InMemoryInventoryAvailabilityAdapter;
import com.cloudplatform.infrastructure.persistence.InMemoryOrderRepository;
import com.cloudplatform.webapi.controller.CreateOrderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ApplicationConfiguration {

    @Bean
    // Singleton: потокобезпечний репозиторій спільно використовує сховище в межах процесу.
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    // Singleton: адаптер не зберігає стан конкретного HTTP-запиту.
    public InventoryAvailabilityPort inventoryAvailabilityPort() {
        return new InMemoryInventoryAvailabilityAdapter();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    // Transient: сценарій не зберігає стан між викликами.
    public CreateOrderUseCase createOrderUseCase(
            OrderRepository orderRepository,
            InventoryAvailabilityPort inventoryAvailabilityPort
    ) {
        return new CreateOrderUseCase(orderRepository, inventoryAvailabilityPort);
    }

    @Bean
    @RequestScope
    // Scoped: контролер існує в межах одного HTTP-запиту та не переносить стан між запитами.
    public CreateOrderController createOrderController(CreateOrderUseCase useCase) {
        return new CreateOrderController(useCase);
    }
}