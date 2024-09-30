package com.TDDev.Spring.Boot.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TDDev.Spring.Boot.Project.entity.ImageData;

@Repository
public interface StorageRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String fileName);
}
