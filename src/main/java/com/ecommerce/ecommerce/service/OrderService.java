package com.ecommerce.ecommerce.service;


import com.ecommerce.ecommerce.dto.OrderItemResponseDto;
import com.ecommerce.ecommerce.dto.OrderRequestDto;
import com.ecommerce.ecommerce.dto.OrderResponseDto;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    public OrderService(UserRepo userRepo, AddressRepo addressRepo, OrderRepo orderRepo,
                        OrderItemRepo orderItemRepo, CartRepo cartRepo, CartItemRepo cartItemRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto dto) throws Exception {

        UserEntity user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new Exception("User not found"));

        AddressEntity address = addressRepo.findById(dto.addressId())
                .orElseThrow(() -> new Exception("Address not found"));

        CartEntity cart = user.getCart();

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setCreatedAt(new Date());
        order.setStatus("SUCCESS");

        List<OrderItemEntity> items = new ArrayList<>();
        double total = 0;

        for (CartItemEntity ci : cart.getCartItems()) {
            OrderItemEntity oi = new OrderItemEntity();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getProduct().getPrice());
            items.add(oi);

            total += ci.getQuantity() * ci.getProduct().getPrice();
        }

        order.setTotalAmount(total);
        order.setOrderItems(items);

        OrderEntity savedOrder = orderRepo.save(order);

        // clear cart
        cartItemRepo.deleteAll(cart.getCartItems());

        return buildResponse(savedOrder);
    }

    public OrderResponseDto getOrder(Long orderId) throws Exception {
        return orderRepo.findById(orderId)
                .map(this::buildResponse)
                .orElseThrow(() -> new Exception("Order not found"));
    }

    public List<OrderResponseDto> getUserOrders(Long userId) {
        return orderRepo.findByUser_Id(userId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }
    @Transactional
    public Boolean updateStatus(Long orderId, String value) {
        OrderEntity order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return false;

        order.setStatus(value);
        orderRepo.save(order);
        return true;
    }

    private OrderResponseDto buildResponse(OrderEntity order) {
        List<OrderItemResponseDto> items = order.getOrderItems()
                .stream()
                .map(oi -> new OrderItemResponseDto(
                        oi.getProduct().getProductId(),
                        oi.getProduct().getProductName(),
                        oi.getQuantity(),
                        oi.getPrice()
                )).toList();

        return new OrderResponseDto(
                order.getOrderId(),
                order.getTotalAmount(),
                order.getStatus(),
                items
        );
    }
}

