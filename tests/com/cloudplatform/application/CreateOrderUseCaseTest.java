package com.cloudplatform.application;

import com.cloudplatform.application.port.InventoryAvailabilityPort;
import com.cloudplatform.application.usecase.CreateOrderCommand;
import com.cloudplatform.application.usecase.CreateOrderUseCase;
import com.cloudplatform.domain.model.Order;
import com.cloudplatform.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryAvailabilityPort inventoryPort;

    private CreateOrderUseCase useCase;

    private final UUID customerId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        useCase = new CreateOrderUseCase(orderRepository, inventoryPort);
    }

    private CreateOrderCommand validCommand() {
        return new CreateOrderCommand(
                customerId,
                List.of(new CreateOrderCommand.OrderLine(
                        productId, 2, new BigDecimal("150.00")
                )),
                new CreateOrderCommand.ShippingAddress(
                        "Ukraine", "Kherson", "Centralna", "10", "73000"
                )
        );
    }

    @Test
    void execute_validCommand_checksInventoryAndSavesOrder() {
        when(inventoryPort.isAvailable(productId, 2)).thenReturn(true);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = useCase.execute(validCommand());

        assertNotNull(result.getId());
        assertEquals(customerId, result.getCustomerId());
        assertEquals(new BigDecimal("300.00"), result.getTotalAmount());
        assertEquals("PENDING", result.getStatus().name());

        verify(inventoryPort).isAvailable(productId, 2);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void execute_emptyItems_rejectsCommandWithoutSaving() {
        CreateOrderCommand invalidCommand = new CreateOrderCommand(
                customerId,
                List.of(),
                new CreateOrderCommand.ShippingAddress(
                        "Ukraine", "Kherson", "Centralna", "10", "73000"
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(invalidCommand)
        );

        verifyNoInteractions(inventoryPort);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void execute_inventoryPortFailure_propagatesToCaller() {
        when(inventoryPort.isAvailable(productId, 2))
                .thenThrow(new IllegalStateException("Inventory port unavailable"));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(validCommand())
        );

        assertEquals("Inventory port unavailable", exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
    }
}