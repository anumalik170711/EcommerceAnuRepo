package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.dto.AddressDto;
import com.ecommerce.ecommerce.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<AddressEntity,Long> {
    public List<AddressEntity> findAllAddressesByUserId(Long userId);
}
