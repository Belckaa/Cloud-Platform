package com.cloudplatform.webapi.controller;

import com.cloudplatform.application.usecase.CreateOrderCommand;
import com.cloudplatform.application.usecase.CreateOrderUseCase;
import com.cloudplatform.domain.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class CreateOrderController {
    private final CreateOrderUseCase createOrderUseCase;

    public CreateOrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(
            @RequestBody CreateOrderRequest request
    ) {
        CreateOrderCommand command = new CreateOrderCommand(
                request.customerId(),
                request.items().stream()
                        .map(item -> new CreateOrderCommand.OrderLine(
                                item.productId(), item.quantity(), item.unitPrice()
                        ))
                        .toList(),
                new CreateOrderCommand.ShippingAddress(
                        request.shippingAddress().country(),
                        request.shippingAddress().city(),
                        request.shippingAddress().street(),
                        request.shippingAddress().building(),
                        request.shippingAddress().postalCode()
                )
        );

        Order order = createOrderUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CreateOrderResponse(
                        order.getId(),
                        order.getCustomerId(),
                        order.getStatus().name(),
                        order.getTotalAmount(),
                        order.getCreatedAt()
                )
        );
    }

    public record CreateOrderRequest(
            UUID customerId,
            List<OrderItemRequest> items,
            AddressRequest shippingAddress
    ) {}

    public record OrderItemRequest(
            UUID productId,
            int quantity,
            BigDecimal unitPrice
    ) {}

    public record AddressRequest(
            String country,
            String city,
            String street,
            String building,
            String postalCode
    ) {}

    public record CreateOrderResponse(
            UUID id,
            UUID customerId,
            String status,
            BigDecimal totalAmount,
            Instant createdAt
    ) {}
}