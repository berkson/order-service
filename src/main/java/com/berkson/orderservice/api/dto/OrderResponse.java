package com.berkson.orderservice.api.dto;

import com.berkson.orderservice.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID customerId,
        OrderStatus status,
        BigDecimal total,
        Instant createdAt,
        List<OrderItemResponse> items)
{}
