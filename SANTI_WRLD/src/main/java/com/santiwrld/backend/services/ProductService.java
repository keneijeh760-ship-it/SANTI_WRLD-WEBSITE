package com.santiwrld.backend.services;

import com.santiwrld.backend.dtos.CreateProductDTO;
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
    @Transactional
    public Product createProduct(CreateProductDTO product){
        if (productRepository.findBySlug(product.getSlug()).isPresent()){
            throw new IllegalStateException("Product already exists");
        }

        Product productEntity = Product.builder()
                .slug(product.getSlug())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .collection(product.getCollection())
                .isActive(true)
                .stockQuantity(0).build();
        return productRepository.save(productEntity);


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
                .slug(product.getSlug())
                .productName(dto.getProductName())
                .description(dto.getProductDescription())
                .price(dto.getProductPrice())
                .isActive(dto.getActive())
                .collection(product.getCollection())
                .stockQuantity(dto.getStockQuantity())
                .imageUrl(dto.getImageUrl())
                .build();

        return  productRepository.save(product);
    }


}
