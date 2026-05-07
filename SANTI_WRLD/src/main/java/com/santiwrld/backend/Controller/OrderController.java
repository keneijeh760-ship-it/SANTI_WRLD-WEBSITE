package com.santiwrld.backend.Controller;

import com.santiwrld.backend.dtos.CheckoutRequestDTO;
import com.santiwrld.backend.dtos.OrderItemDTO;
import com.santiwrld.backend.dtos.OrderResponseDTO;
import com.santiwrld.backend.entities.Order;
import com.santiwrld.backend.entities.OrderItem;
import com.santiwrld.backend.entities.OrderStatus;
import com.santiwrld.backend.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);

    }

    @GetMapping("{reference}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable String reference){
        Order order = orderService.getByReference(reference);

        OrderResponseDTO responseDTO = OrderResponseDTO
                .builder()
                .id(order.getId())
                .orderReference(order.getOrderReference())
                .customerEmail(order.getCustomerEmail())
                .totalPrice(order.getTotalPrice())
                .customerName(order.getCustomerName())
                .createdAt(order.getCreatedAt())
                .paymentStatus(order.getPaymentStatus())
                .displayTotalPrice(order.getTotalPrice().toString())
                .status(order.getOrderStatus())
                .items(maptodto(order.getOrderItems()))
                .build();
        return ResponseEntity.ok(responseDTO);


    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByEmail(@RequestParam @Valid  String email){
        List<Order> orders = orderService.getByEmail(email);

        List<OrderResponseDTO> ordersList = new ArrayList<>();

        for (Order order : orders) {
            OrderResponseDTO responseDTO = OrderResponseDTO
                    .builder()
                    .totalPrice(order.getTotalPrice())
                    .orderReference(order.getOrderReference())
                    .id(order.getId())
                    .status(order.getOrderStatus())
                    .displayTotalPrice(order.getTotalPrice().toString())
                    .customerEmail(order.getCustomerEmail())
                    .paymentStatus(order.getPaymentStatus())
                    .customerName(order.getCustomerName())
                    .createdAt(order.getCreatedAt())
                    .items(maptodto(order.getOrderItems()))
                    .build();

            ordersList.add(responseDTO);
        }

        return ResponseEntity.ok(ordersList);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO>  updateStatus (@Valid @PathVariable Long id, @RequestParam @Valid  OrderStatus orderStatus){
        Order order = orderService.updateStatus(id, orderStatus);

        OrderResponseDTO responseDTO = OrderResponseDTO
                .builder()
                .totalPrice(order.getTotalPrice())
                .orderReference(order.getOrderReference())
                .id(order.getId())
                .status(order.getOrderStatus())
                .displayTotalPrice(order.getTotalPrice().toString())
                .customerEmail(order.getCustomerEmail())
                .paymentStatus(order.getPaymentStatus())
                .customerName(order.getCustomerName())
                .createdAt(order.getCreatedAt())
                .items(maptodto(order.getOrderItems()))
                .build();

        return ResponseEntity.ok(responseDTO);
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
