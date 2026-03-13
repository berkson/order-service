package com.berkson.orderservice.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        Long id,
        UUID productId,
        String productSku,
        String productName,
        int quantity,
        BigDecimal price) {
}
