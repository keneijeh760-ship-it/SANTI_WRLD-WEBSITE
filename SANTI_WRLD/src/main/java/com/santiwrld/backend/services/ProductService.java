package com.santiwrld.backend.services;

import com.santiwrld.backend.dtos.ProductUpdateDTO;
import com.santiwrld.backend.entities.Product;
import com.santiwrld.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllActive(){

        List<Product> active =  productRepository.findByActiveTrue();
        return active;
    }

    public Product getBySlug(String slug){
        Product sluggy = productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return sluggy;
    }

    public List<Product> getByCollection(String collection){
        List<Product> collect =  productRepository.findByCollection(collection);

        if (collect.isEmpty()){
            return List.of();
        }

        return collect;
    }

    public Product update(Long id, ProductUpdateDTO dto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product = Product
                .builder()
                .Id(product.getId())
                .productName(dto.getProductName())
                .description(dto.getProductDescription())
                .price(dto.getProductPrice())
                .isActive(dto.getActive())
                .stockQuantity(dto.getStockQuantity())
                .imageUrl(dto.getImageUrl())
                .build();

        return  productRepository.save(product);
    }


}
