package com.berkson.orderservice.api.controller.error;

import com.berkson.orderservice.api.dto.error.ApiErrorResponse;
import com.berkson.orderservice.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(OrderNotFoundException ex, HttpServletRequest req) {
        return new ApiErrorResponse(Instant.now(), 404, "Not Found", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler({
            InvalidCustomerException.class,
            InvalidProductException.class,
            CustomerBlockedException.class,
            ProductInactiveException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ApiErrorResponse handleUnprocessable(RuntimeException ex, HttpServletRequest req) {
        return new ApiErrorResponse(Instant.now(), 422, "Unprocessable Content", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        return new ApiErrorResponse(Instant.now(), 400, "Bad Request", "Validation failed", req.getRequestURI());
    }
}
