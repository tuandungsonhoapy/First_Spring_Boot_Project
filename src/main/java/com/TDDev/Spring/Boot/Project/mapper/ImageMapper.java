package com.TDDev.Spring.Boot.Project.mapper;

import com.TDDev.Spring.Boot.Project.dto.response.ImageResponse;
import com.TDDev.Spring.Boot.Project.entity.ImageData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "data", ignore = true)
    ImageResponse toImageResponse(ImageData imageData);
}
