package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.PaymentInitiateRequestDto;
import com.ecommerce.ecommerce.dto.PaymentResponseDto;
import com.ecommerce.ecommerce.dto.PaymentVerifyRequestDto;
import com.ecommerce.ecommerce.entity.OrderEntity;
import com.ecommerce.ecommerce.entity.PaymentEntity;
import com.ecommerce.ecommerce.repository.OrderRepo;
import com.ecommerce.ecommerce.repository.PaymentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;

    public PaymentService(PaymentRepo paymentRepo, OrderRepo orderRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
    }

    // Step 1: Initiate Payment (Fake RazorPay)
    @Transactional
    public PaymentResponseDto initiatePayment(PaymentInitiateRequestDto dto) throws Exception {

        OrderEntity order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new Exception("Order not found"));

        // create pending payment
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(order.getTotalAmount());
        payment.setStatus("PENDING");
        payment.setOrder(order);

        // fake transactionId
        String txnId = "TXN-" + System.currentTimeMillis();
        payment.setTransactionId(txnId);

        PaymentEntity saved = paymentRepo.save(payment);

        return new PaymentResponseDto(
                saved.getPaymentId(),
                saved.getOrder().getOrderId(),
                saved.getAmount(),
                saved.getStatus(),
                saved.getTransactionId()
        );
    }

    // Step 2: Verify Payment (Webhook Simulation)
    @Transactional
    public PaymentResponseDto verifyPayment(PaymentVerifyRequestDto dto) throws Exception {

        PaymentEntity payment = paymentRepo.findById(dto.paymentId())
                .orElseThrow(() -> new Exception("Payment not found"));

        if (!payment.getTransactionId().equals(dto.transactionId())) {
            throw new Exception("Invalid transaction ID");
        }

        if (dto.success()) {
            payment.setStatus("SUCCESS");
            payment.getOrder().setStatus("CONFIRMED");  // update order
        } else {
            payment.setStatus("FAILED");
            payment.getOrder().setStatus("PAYMENT_FAILED");
        }

        paymentRepo.save(payment);
        orderRepo.save(payment.getOrder());

        return new PaymentResponseDto(
                payment.getPaymentId(),
                payment.getOrder().getOrderId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getTransactionId()
        );
    }

    // Step 3: Check Payment Status
    public String getPaymentStatus(Long orderId) throws Exception {

        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        PaymentEntity payment = order.getPayment();

        return payment == null
                ? "NO PAYMENT FOUND"
                : payment.getStatus();
    }
}

