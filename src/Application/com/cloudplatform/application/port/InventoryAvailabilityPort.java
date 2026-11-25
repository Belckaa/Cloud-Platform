package com.cloudplatform.application.port;

import java.util.UUID;

public interface InventoryAvailabilityPort {
    boolean isAvailable(UUID productId, int quantity);
}