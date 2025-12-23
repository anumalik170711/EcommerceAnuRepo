package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<CartEntity,Long> {

    CartEntity findByUser_Id(Long userId);

}
