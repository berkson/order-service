package com.berkson.orderservice.integration.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "customerClient", url = "${clients.customer-service.url}")
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    CustomerDto getById(@PathVariable("id") UUID id);
}
