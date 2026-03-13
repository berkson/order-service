package com.berkson.orderservice.exception;

import java.util.UUID;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(UUID productId) {
        super("Invalid productId: " + productId);
    }
}
