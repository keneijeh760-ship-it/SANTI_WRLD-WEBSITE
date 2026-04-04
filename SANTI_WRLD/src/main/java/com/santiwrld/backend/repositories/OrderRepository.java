package com.santiwrld.backend.repositories;

import com.santiwrld.backend.entities.Order;
import com.santiwrld.backend.entities.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderReference(Pageable page, String orderReference);
    Optional<Order> findByCustomerEmail(Pageable page, String customerEmail);

    Optional<Order> findByOrderStatus(OrderStatus orderStatus);
}
