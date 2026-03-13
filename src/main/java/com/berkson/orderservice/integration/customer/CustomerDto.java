package com.berkson.orderservice.integration.customer;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        String name,
        String email,
        String status) {
}
