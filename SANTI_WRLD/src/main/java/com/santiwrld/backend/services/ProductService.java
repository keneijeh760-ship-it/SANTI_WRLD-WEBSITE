package com.santiwrld.backend.services;

import com.santiwrld.backend.entities.Product;
import com.santiwrld.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public Product getAllActive(){

        List<Product> active =  productRepository.findByActiveTrue();
        return active.stream().findFirst().orElse(null);
    }

    public Product getBySlug(String slug){
        Product sluggy = productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return sluggy;
    }

    public Product getByCollection(String collection){
        Product collect =  productRepository.findByCollection(collection)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return collect;
    }

    public Product create(Product product){}


}
