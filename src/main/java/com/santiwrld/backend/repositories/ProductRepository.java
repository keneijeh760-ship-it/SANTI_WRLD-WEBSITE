package com.santiwrld.backend.repositories;

import com.santiwrld.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySlug(String slug);
    List<Product> findByActiveTrue();

    List<Product> findByCollection(String collection);
    
}
