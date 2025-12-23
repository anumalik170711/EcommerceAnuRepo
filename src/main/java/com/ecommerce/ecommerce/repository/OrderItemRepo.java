package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItemEntity,Long> {
}
