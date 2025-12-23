package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.AddressDto;
import com.ecommerce.ecommerce.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService=addressService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<AddressDto> addAddress(@RequestBody AddressDto addressDto, @PathVariable Long id) throws Exception {
        AddressDto addressResponseDto = addressService.addAddress(addressDto,id);
        return ResponseEntity.ok(addressResponseDto);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDto>> getAllAddress(@PathVariable Long userId){
        return ResponseEntity.ok(addressService.getAllAddress(userId));
    }
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody AddressDto addressDto,@PathVariable Long addressId) throws Exception {
        return ResponseEntity.ok(addressService.updateAddress(addressDto, addressId));

    }
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Boolean> deleteAddress(@PathVariable Long addressId){
        return ResponseEntity.ok(addressService.deleteAddress(addressId));
    }




}
