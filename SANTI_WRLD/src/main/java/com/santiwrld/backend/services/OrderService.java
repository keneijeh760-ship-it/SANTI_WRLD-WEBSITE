package com.santiwrld.backend.services;

import com.santiwrld.backend.dtos.CheckoutRequestDTO;
import com.santiwrld.backend.entities.Order;
import com.santiwrld.backend.entities.OrderStatus;
import com.santiwrld.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(CheckoutRequestDTO order) {
        Order neworder = Order
                .builder()
                .customerName(order.getFullName())
                .customerEmail(order.getEmail())
                .customerPhone(order.getPhoneNumber())
                .deliveryAddress(order.getAddress())
                .city(order.getCity())
                .state(order.getState())
                .orderItems(order.getItems())
                .orderStatus(OrderStatus.PENDING)
                .build();

        return orderRepository.save(neworder);
    }


    public Order getByReference(String reference) {
        Order ref = orderRepository.findByOrderReference(reference)
                .orElseThrow(()-> new RuntimeException("Order reference not found"));

        return ref;
    }

    public List<Order> getByEmail(String email) {
        List<Order> emailSearch = orderRepository.findByCustomerEmail(email);

        return emailSearch;

    }




}
