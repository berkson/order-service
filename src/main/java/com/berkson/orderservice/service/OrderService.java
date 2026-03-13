package com.berkson.orderservice.service;

import com.berkson.orderservice.api.dto.CreateOrderRequest;
import com.berkson.orderservice.api.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse create(CreateOrderRequest req);

    OrderResponse getById(UUID id);

    List<OrderResponse> listByCustomerId(UUID customerId);
}
