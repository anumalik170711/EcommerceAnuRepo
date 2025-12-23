package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.AddressDto;
import com.ecommerce.ecommerce.entity.AddressEntity;
import com.ecommerce.ecommerce.entity.UserEntity;
import com.ecommerce.ecommerce.repository.AddressRepo;
import com.ecommerce.ecommerce.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AddressService {

    private AddressRepo addressRepo;
    private UserRepo userRepo;

    public AddressService(AddressRepo addressRepo, UserRepo userRepo){
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }
    @Transactional
    public AddressDto addAddress(AddressDto addressDto,Long userId) throws Exception {
        UserEntity userEntity = userRepo.findById(userId).orElseThrow(()->new Exception("No user foud with given Id"));
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(addressDto.city());
        addressEntity.setState(addressDto.state());
        addressEntity.setStreet(addressDto.street());
        addressEntity.setZipcode(addressDto.zipcode());
        addressEntity.setCountry(addressDto.country());
        addressEntity.setUser(userEntity);
        AddressEntity save = addressRepo.save(addressEntity);
        return new AddressDto(save.getAddressId(),save.getStreet(),save.getCity(),save.getState(),save.getZipcode(),save.getCountry());
    }

    public List<AddressDto> getAllAddress(Long userId){
        List<AddressEntity> allAddressesByUserId = addressRepo.findAllAddressesByUserId(userId);
        return allAddressesByUserId.stream().map(address -> new AddressDto(address.getAddressId(),address.getStreet(),address.getCity(),address.getState(),address.getZipcode(),address.getCountry())).toList();
    }
    @Transactional
    public AddressDto updateAddress(AddressDto addressDto,Long addressId) throws Exception {
        AddressEntity addressEntity = addressRepo.findById(addressId).orElseThrow(() -> new Exception("No address found with this id"));
        addressEntity.setStreet(addressDto.street());
        addressEntity.setState(addressDto.state());
        addressEntity.setZipcode(addressDto.zipcode());
        addressEntity.setCity(addressDto.city());
        addressEntity.setCountry(addressDto.country());
        AddressEntity save = addressRepo.save(addressEntity);
        return new AddressDto(save.getAddressId(),save.getStreet(),save.getCity(),save.getState(),save.getZipcode(),save.getCountry());

    }

    public Boolean deleteAddress(Long addressId){
        if(!addressRepo.existsById(addressId)){
            return false;
        }
        addressRepo.deleteById(addressId);
        return true;
    }


}
