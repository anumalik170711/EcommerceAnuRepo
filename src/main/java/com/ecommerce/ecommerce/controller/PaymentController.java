package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.PaymentInitiateRequestDto;
import com.ecommerce.ecommerce.dto.PaymentResponseDto;
import com.ecommerce.ecommerce.dto.PaymentVerifyRequestDto;
import com.ecommerce.ecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDto> initiate(@RequestBody PaymentInitiateRequestDto dto) throws Exception {
        return ResponseEntity.ok(service.initiatePayment(dto));
    }

    @PostMapping("/verify")
    public ResponseEntity<PaymentResponseDto> verify(@RequestBody PaymentVerifyRequestDto dto) throws Exception {
        return ResponseEntity.ok(service.verifyPayment(dto));
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<String> getStatus(@PathVariable Long orderId) throws Exception {
        return ResponseEntity.ok(service.getPaymentStatus(orderId));
    }
}
