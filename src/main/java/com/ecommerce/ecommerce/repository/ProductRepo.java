package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    @Query("SELECT p from ProductEntity p where p.category.category = :category")
    Page<ProductEntity> findProductsByCategory(Long category, Pageable pageable);
}
