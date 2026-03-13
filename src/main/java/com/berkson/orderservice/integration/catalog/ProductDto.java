package com.berkson.orderservice.integration.catalog;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(
        UUID id,
        String sku,
        String name,
        BigDecimal price,
        boolean active) {
}
