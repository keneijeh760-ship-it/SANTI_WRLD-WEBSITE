package com.santiwrld.backend.Controller;

import com.santiwrld.backend.dtos.CreateProductDTO;
import com.santiwrld.backend.dtos.ProductResponseDTO;
import com.santiwrld.backend.dtos.ProductUpdateDTO;
import com.santiwrld.backend.entities.Product;
import com.santiwrld.backend.repositories.ProductRepository;
import com.santiwrld.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody CreateProductDTO product) {
         Product service = productService.createProduct(product);
         ProductResponseDTO response = ProductResponseDTO.builder()
                 .id(service.getId())
                 .slug(service.getSlug())
                 .name(service.getProductName())
                 .description(service.getDescription())
                 .price(service.getPrice())
                 .displayPrice(service.getPrice().toString())
                 .imageUrl(service.getImageUrl())
                 .collection(service.getCollection())
                 .build();

         return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid ProductUpdateDTO product, @PathVariable Long id) {
        Product update = productService.update(id,  product);
        ProductResponseDTO responseDTO =  ProductResponseDTO.builder()
                .id(update.getId())
                .slug(update.getSlug())
                .name(update.getProductName())
                .description(update.getDescription())
                .price(update.getPrice())
                .displayPrice(update.getPrice().toString())
                .imageUrl(update.getImageUrl())
                .collection(update.getCollection())
                .build();

        return ResponseEntity.ok(responseDTO);
    }


}
