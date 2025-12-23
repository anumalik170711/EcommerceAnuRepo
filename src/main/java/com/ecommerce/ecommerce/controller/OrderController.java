package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.OrderRequestDto;
import com.ecommerce.ecommerce.dto.OrderResponseDto;
import com.ecommerce.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto dto) throws Exception {
        return ResponseEntity.ok(service.placeOrder(dto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) throws Exception {
        return ResponseEntity.ok(service.getOrder(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserOrders(userId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Boolean> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String value) {
        return ResponseEntity.ok(service.updateStatus(orderId, value));
    }
}

