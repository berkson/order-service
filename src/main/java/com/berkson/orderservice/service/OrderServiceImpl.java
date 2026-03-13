package com.berkson.orderservice.service;

import com.berkson.orderservice.api.dto.CreateOrderRequest;
import com.berkson.orderservice.api.dto.OrderItemResponse;
import com.berkson.orderservice.api.dto.OrderResponse;
import com.berkson.orderservice.domain.Order;
import com.berkson.orderservice.domain.OrderItem;
import com.berkson.orderservice.exception.*;
import com.berkson.orderservice.integration.catalog.CatalogClient;
import com.berkson.orderservice.integration.catalog.ProductDto;
import com.berkson.orderservice.integration.customer.CustomerClient;
import com.berkson.orderservice.integration.customer.CustomerDto;
import com.berkson.orderservice.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final CatalogClient catalogClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponse create(CreateOrderRequest req) {
        CustomerDto customer = null;
        try {
            customer = customerClient.getById(req.customerId());
        } catch (FeignException.NotFound e) {
            throw new InvalidCustomerException(req.customerId());
        }
        if ("BLOCKED".equalsIgnoreCase(customer.status())) {
            throw new CustomerBlockedException(customer.id());
        }

        Order order = new Order();
        order.setCustomerId(customer.id());

        BigDecimal total = BigDecimal.ZERO;

        for (var itemReq : req.items()) {
            ProductDto product = null;
            try {
                product = catalogClient.getById(itemReq.productId());
            } catch (FeignException.NotFound e) {
                throw new InvalidProductException(itemReq.productId());
            }
            if (!product.active()) {
                throw new ProductInactiveException(product.id());
            }

            OrderItem item = new OrderItem();
            item.setProductId(product.id());
            item.setProductSku(product.sku());
            item.setProductName(product.name());
            item.setQuantity(itemReq.quantity());
            item.setPrice(product.price());
            order.addItem(item);
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())));
        }

        order.setTotal(total);
        order = repository.save(order);

        return toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getById(UUID id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> listByCustomerId(UUID customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(i -> new OrderItemResponse(i.getId(), i.getProductId(), i.getProductSku(),
                        i.getProductName(), i.getQuantity(), i.getPrice()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                items
        );
    }
}
