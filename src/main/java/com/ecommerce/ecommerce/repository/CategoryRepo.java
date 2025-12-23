package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity,Long> {
    CategoryEntity findCategoryByName(String categoryName);
}
