package com.berkson.orderservice.integration.catalog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "catalogClient", url = "${clients.catalog-service.url}")
public interface CatalogClient {
    @GetMapping("/products/{id}")
    ProductDto getById(@PathVariable("id") UUID id);
}
