package com.TDDev.Spring.Boot.Project.dto.response;

import java.util.List;

import com.TDDev.Spring.Boot.Project.entity.ImageData;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    long id;
    String name;
    double price;
    String description;
    List<ImageData> imageDataList;
}
