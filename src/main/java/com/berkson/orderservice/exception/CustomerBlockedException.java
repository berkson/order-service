package com.berkson.orderservice.exception;

import java.util.UUID;

public class CustomerBlockedException extends RuntimeException {
    public CustomerBlockedException(UUID customerId) {
        super("Customer is blocked: " + customerId);
    }
}
