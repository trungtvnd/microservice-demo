package com.trungtv.productservice.service;

import com.trungtv.productservice.dto.ProductRequest;
import com.trungtv.productservice.dto.ProductResponse;
import com.trungtv.productservice.model.Product;
import com.trungtv.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info(" Product {} is save  ", product.getId());
    }

    public List<ProductResponse> getListProduct(){
        List<ProductResponse> products = productRepository.findAll()
                .stream().map(this::mapToProductResponse).collect(Collectors.toList());
        return products;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
        .build();
    }

}
