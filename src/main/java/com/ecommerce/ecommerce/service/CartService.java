package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.CartItemRequestDto;
import com.ecommerce.ecommerce.dto.CartItemResponseDto;
import com.ecommerce.ecommerce.dto.CartResponseDto;
import com.ecommerce.ecommerce.entity.CartEntity;
import com.ecommerce.ecommerce.entity.CartItemEntity;
import com.ecommerce.ecommerce.entity.ProductEntity;
import com.ecommerce.ecommerce.entity.UserEntity;
import com.ecommerce.ecommerce.repository.CartItemRepo;
import com.ecommerce.ecommerce.repository.CartRepo;
import com.ecommerce.ecommerce.repository.ProductRepo;
import com.ecommerce.ecommerce.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;

    public CartService(UserRepo userRepo, ProductRepo productRepo,
                       CartRepo cartRepo, CartItemRepo cartItemRepo) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }
    @Transactional
    public CartResponseDto addProductToCart(CartItemRequestDto dto) throws Exception {
        UserEntity user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new Exception("User not found"));

        CartEntity cart = user.getCart();
        ProductEntity product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new Exception("Product not found"));

        // check if product already exists in cart
        CartItemEntity existing = cartItemRepo.findByCartIdAndProductId(cart.getCartId(), product.getProductId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + dto.quantity());
            cartItemRepo.save(existing);
        } else {
            CartItemEntity item = new CartItemEntity();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(dto.quantity());
            cartItemRepo.save(item);
        }

        return buildCartResponse(cart);
    }
    @Transactional
    public CartResponseDto updateQuantity(Long cartItemId, int quantity) throws Exception {
        CartItemEntity item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new Exception("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepo.save(item);

        return buildCartResponse(item.getCart());
    }

    public CartResponseDto removeItem(Long cartItemId) {
        CartItemEntity item = cartItemRepo.findById(cartItemId).orElse(null);
        if (item == null) return null;

        CartEntity cart = item.getCart();
        cartItemRepo.delete(item);

        return buildCartResponse(cart);
    }

    public CartResponseDto getCartByUser(Long userId) throws Exception {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        return buildCartResponse(user.getCart());
    }

    public Boolean clearCart(Long userId) {
        CartEntity cart = cartRepo.findByUser_Id(userId);
        if (cart == null) return false;

        cartItemRepo.deleteAll(cart.getCartItems());
        return true;
    }

    private CartResponseDto buildCartResponse(CartEntity cart) {
        List<CartItemResponseDto> items = cart.getCartItems().stream()
                .map(ci -> new CartItemResponseDto(
                        ci.getCartItemId(),
                        ci.getProduct().getProductId(),
                        ci.getProduct().getProductName(),
                        ci.getProduct().getPrice(),
                        ci.getQuantity()
                )).toList();

        double total = items.stream().mapToDouble(i -> i.price() * i.quantity()).sum();

        return new CartResponseDto(cart.getCartId(), items, total);
    }
}


