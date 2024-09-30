package com.TDDev.Spring.Boot.Project.controller;

import com.TDDev.Spring.Boot.Project.service.ProductService;
import com.TDDev.Spring.Boot.Project.service.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;
    StorageService storageService;

    @PostMapping("/create-product")
    public String createProduct(String name, double price, String description) {
        return productService.createProduct(name, price, description).toString();
    }
}
