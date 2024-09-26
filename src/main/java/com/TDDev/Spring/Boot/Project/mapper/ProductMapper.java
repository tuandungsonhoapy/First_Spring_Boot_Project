package com.TDDev.Spring.Boot.Project.mapper;

import com.TDDev.Spring.Boot.Project.dto.response.ProductResponse;
import com.TDDev.Spring.Boot.Project.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
}
