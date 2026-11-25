package com.cloudplatform.infrastructure.inventory;

import com.cloudplatform.application.port.InventoryAvailabilityPort;

import java.util.UUID;

public final class InMemoryInventoryAvailabilityAdapter
        implements InventoryAvailabilityPort {

    @Override
    public boolean isAvailable(UUID productId, int quantity) {
        // Демонстраційний адаптер: база даних і мережеві виклики не використовуються.
        return true;
    }
}