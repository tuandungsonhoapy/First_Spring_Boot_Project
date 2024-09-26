package com.TDDev.Spring.Boot.Project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;
    String type;
    @Lob
    @Column(name = "imageData")
    byte[] data;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
