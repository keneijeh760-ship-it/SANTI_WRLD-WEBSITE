package com.santiwrld.backend.repositories;

import com.santiwrld.backend.entities.Order;
import com.santiwrld.backend.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderReference( String orderReference);
    List<Order> findByCustomerEmail(String customerEmail);

    Optional<Order> findByOrderStatus(OrderStatus orderStatus);
}
