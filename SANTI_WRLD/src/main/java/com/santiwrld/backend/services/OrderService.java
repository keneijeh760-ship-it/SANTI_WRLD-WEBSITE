package com.santiwrld.backend.services;

import com.santiwrld.backend.dtos.CheckoutRequestDTO;
import com.santiwrld.backend.dtos.OrderItemDTO;
import com.santiwrld.backend.entities.Order;
import com.santiwrld.backend.entities.OrderItem;
import com.santiwrld.backend.entities.OrderStatus;
import com.santiwrld.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .orderItems(order.g)
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

    public void updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        if (order.getOrderStatus().equals(status)) {
            throw new RuntimeException("Order status already set");
        }

        order.setOrderStatus(status);
    }

    private List<OrderItemDTO> maptodto (List<OrderItem> orderItems){

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for (OrderItem orderItem : orderItems){
            OrderItemDTO dto = OrderItemDTO.builder()
                    .productName(orderItem.getProductName())
                    .price(orderItem.getPrice())
                    .quantity(orderItem.getQuantity())
                    .build();
            orderItemDTOS.add(dto);

        }

        return orderItemDTOS;

    }




}
