package com.TDDev.Spring.Boot.Project.mapper;

import org.mapstruct.Mapper;

import com.TDDev.Spring.Boot.Project.dto.response.ProductResponse;
import com.TDDev.Spring.Boot.Project.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
}
