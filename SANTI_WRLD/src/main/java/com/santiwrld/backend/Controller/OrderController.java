package com.santiwrld.backend.Controller;

import com.santiwrld.backend.dtos.CheckoutRequestDTO;
import com.santiwrld.backend.dtos.OrderItemDTO;
import com.santiwrld.backend.dtos.OrderResponseDTO;
import com.santiwrld.backend.entities.Order;
import com.santiwrld.backend.entities.OrderItem;
import com.santiwrld.backend.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> createOrder (@Valid @RequestBody CheckoutRequestDTO checkoutRequestDTO){
        Order order = orderService.createOrder(checkoutRequestDTO);



        OrderResponseDTO orderResponse = OrderResponseDTO
                .builder()
                .id(order.getId())
                .orderReference(order.getOrderReference())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .totalPrice(order.getTotalPrice())
                .displayTotalPrice(order.getTotalPrice().toString())

                .items(maptodto(order.getOrderItems()))
                .build();

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
