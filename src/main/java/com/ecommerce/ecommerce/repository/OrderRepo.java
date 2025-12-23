package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<OrderEntity,Long> {

    List<OrderEntity> findByUser_Id(Long userId);

}
