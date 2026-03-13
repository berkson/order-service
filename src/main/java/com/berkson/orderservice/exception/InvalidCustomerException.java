package com.berkson.orderservice.exception;

import java.util.UUID;

public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException(UUID customerId) {
        super("Invalid customerId: " + customerId);
    }
}
