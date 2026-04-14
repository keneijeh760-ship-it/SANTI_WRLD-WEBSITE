package com.santiwrld.backend.services;

import com.santiwrld.backend.dtos.CartItemDTO;
import com.santiwrld.backend.dtos.CheckoutRequestDTO;
import com.santiwrld.backend.dtos.OrderItemDTO;
import com.santiwrld.backend.entities.*;
import com.santiwrld.backend.repositories.OrderRepository;
import com.santiwrld.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Transactional
    public Order createOrder(CheckoutRequestDTO order) {
        List<OrderItem> lines = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDTO line : order.getItems()) {
            Product p = productRepository.findBySlug(line.getSlug()).orElseThrow(() -> new RuntimeException("Product not found"));
            OrderItem oi = new OrderItem(); // or builder
            oi.setProduct(p);
            oi.setProductName(p.getProductName());
            oi.setPrice(p.getPrice());
            oi.setQuantity(line.getQuantity());
            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())));
            lines.add(oi);
        }

        Order neworder = Order
                .builder()
                .customerName(order.getFullName())
                .customerEmail(order.getEmail())
                .customerPhone(order.getPhoneNumber())
                .deliveryAddress(order.getAddress())
                .city(order.getCity())
                .state(order.getState())
                .orderItems(lines)
                .orderStatus(OrderStatus.PENDING)
                .orderReference("SW-" + UUID.randomUUID().toString())
                .paymentStatus(PaymentStatus.PENDING)
                .user()
                .paymentReference(null)
                .totalPrice(total)
                .build();

        for (OrderItem oi : neworder.getOrderItems()) {
            oi.setOrder(neworder);
        }


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

    public Order updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        if (order.getOrderStatus().equals(status)) {
            throw new RuntimeException("Order status already set");
        }

        order.setOrderStatus(status);

        return orderRepository.save(order);
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
