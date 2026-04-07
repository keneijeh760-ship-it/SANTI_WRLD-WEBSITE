package com.santiwrld.backend.Controller;

import com.santiwrld.backend.dtos.ProductResponseDTO;
import com.santiwrld.backend.entities.Product;
import com.santiwrld.backend.repositories.ProductRepository;
import com.santiwrld.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>>  getAllProducts() {
        List<Product> products = productService.getAllActive();

        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO productResponseDTO = ProductResponseDTO
                    .builder()
                    .id(product.getId())
                    .slug(product.getSlug())
                    .name(product.getProductName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .displayPrice(product.getPrice().toString())
                    .imageUrl(product.getImageUrl())
                    .collection(product.getCollection())
                    .build();

            productResponseDTOS.add(productResponseDTO);
        }

        return ResponseEntity.ok(productResponseDTOS);


    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String slug) {

        Product product = productService.getBySlug(slug);

        ProductResponseDTO productResponse = ProductResponseDTO.builder()
                .id(product.getId())
                .slug(product.getSlug())
                .name(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .displayPrice(product.getPrice().toString())
                .imageUrl(product.getImageUrl())
                .collection(product.getCollection())
                .build();

        return ResponseEntity.ok(productResponse);

    }



}
