package com.TDDev.Spring.Boot.Project.service;

import com.TDDev.Spring.Boot.Project.dto.response.ProductResponse;
import com.TDDev.Spring.Boot.Project.entity.Product;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.mapper.ProductMapper;
import com.TDDev.Spring.Boot.Project.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    public ProductResponse createProduct(String name, double price, String description){
        if (productRepository.findByName(name).isPresent()) throw new AppException(ErrorCode.PRODUCT_EXISTED);

        return productMapper.toProductResponse(productRepository.save(Product.builder()
                .description(description)
                .price(price)
                .name(name)
                .build()));
    }
}
