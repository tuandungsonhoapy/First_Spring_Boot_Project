package com.TDDev.Spring.Boot.Project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;
    String type;
    String filePath;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
