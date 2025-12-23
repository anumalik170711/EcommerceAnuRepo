package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepo extends JpaRepository<CartItemEntity,Long> {

    @Query("SELECT ci FROM CartItemEntity ci WHERE ci.cart.cartId = :cartId AND ci.product.productId = :productId")
    CartItemEntity findByCartIdAndProductId(Long cartId, Long productId);
}
