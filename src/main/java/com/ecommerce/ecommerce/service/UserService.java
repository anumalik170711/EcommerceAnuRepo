package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.LoginResponseDto;
import com.ecommerce.ecommerce.dto.UserRequestDto;
import com.ecommerce.ecommerce.dto.UserResponseDto;
import com.ecommerce.ecommerce.entity.CartEntity;
import com.ecommerce.ecommerce.entity.UserEntity;
import com.ecommerce.ecommerce.repository.UserRepo;
import com.ecommerce.ecommerce.securityConfig.CustomUserDetails;
import com.ecommerce.ecommerce.securityConfig.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Service
public class UserService {
    private UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo repo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = repo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        // Registration logic goes here
        UserEntity userEntity =  new UserEntity();
        userEntity.setName(userRequestDto.name());
        userEntity.setEmail(userRequestDto.email());
        userEntity.setPassword(passwordEncoder.encode(userRequestDto.password()));
        //userEntity.setPassword((userRequestDto.password()));
        userEntity.setPhoneNumber(userRequestDto.phoneNumber());
        userEntity.setCreateAt(new Date(System.currentTimeMillis()).toString());
        userEntity.setUpdatedAt(new Date(System.currentTimeMillis()).toString());
        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>());
        cart.setUser(userEntity);
        userEntity.setCart(cart);
        UserEntity save = userRepo.save(userEntity);

        return new UserResponseDto(save.getName(), save.getEmail(), save.getPhoneNumber());
    }

    public LoginResponseDto login(UserRequestDto dto) {

        UserEntity user = userRepo.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new LoginResponseDto(user.getId(), jwtService.generateToken(new CustomUserDetails(user)));
    }



    public UserResponseDto getUserById(Long id){
        UserEntity userEntity = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        return new UserResponseDto(userEntity.getName(), userEntity.getEmail(), userEntity.getPhoneNumber());
    }
}
