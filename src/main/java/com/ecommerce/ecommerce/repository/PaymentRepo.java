package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<PaymentEntity,Long> {

    PaymentEntity findByTransactionId(String transactionId);
}
