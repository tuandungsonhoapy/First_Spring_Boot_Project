package com.TDDev.Spring.Boot.Project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import com.TDDev.Spring.Boot.Project.dto.response.ImageResponse;
import com.TDDev.Spring.Boot.Project.entity.FileData;
import com.TDDev.Spring.Boot.Project.entity.Product;
import com.TDDev.Spring.Boot.Project.repository.FileRepository;
import com.TDDev.Spring.Boot.Project.repository.ProductRepository;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TDDev.Spring.Boot.Project.entity.ImageData;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.repository.StorageRepository;
import com.TDDev.Spring.Boot.Project.util.ImageUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StorageService {
    StorageRepository storageRepository;
    ProductRepository productRepository;
    FileRepository fileRepository;

    @NonFinal
    @Value("${file.upload-dir}")
    protected String folderPath;

    public String uploadImage(MultipartFile file, long product_id) throws IOException {
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String generatedFileName = UUID.randomUUID().toString() + fileExtension;

        storageRepository.save(ImageData.builder()
                .name(generatedFileName)
                .type(file.getContentType())
                .data(ImageUtils.compressImage(file.getBytes()))
                .product(product)
                .build());
        return "File uploaded successfully! File: " + file.getOriginalFilename();
    }

    public byte[] downloadImage(String fileName) {
        ImageData imageData =
                storageRepository.findByName(fileName).orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXIST));
        return ImageUtils.decompressImage(imageData.getData());
    }

    public String uploadImageToFileSystem(MultipartFile file, long product_id) throws IOException {
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Tạo tên file với UUID
        String generatedFileName = UUID.randomUUID().toString() + fileExtension;
        var filePath = folderPath + generatedFileName;

        // Tạo thư mục nếu chưa tồn tại
        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Lưu file vào hệ thống tệp
        File destFile = new File(filePath);
//        file.transferTo(destFile);
        Files.write(destFile.toPath(), file.getBytes());
        log.info("File path: {}", filePath);

        // Lưu metadata vào database
        fileRepository.save(FileData.builder()
                .name(generatedFileName)
                .type(file.getContentType())
                .filePath(filePath)
                .product(product)
                .build());

        return "http://localhost:8080/identity/images/static/" + generatedFileName;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        FileData fileData = fileRepository.findByName(fileName)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXIST));

        String filePath = fileData.getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }
}
