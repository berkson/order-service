package com.berkson.orderservice.api.controller;

import com.berkson.orderservice.api.dto.CreateOrderRequest;
import com.berkson.orderservice.api.dto.OrderResponse;
import com.berkson.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody @Valid CreateOrderRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<OrderResponse> list(@RequestParam UUID customerId) {
        return service.listByCustomerId(customerId);
    }
}
