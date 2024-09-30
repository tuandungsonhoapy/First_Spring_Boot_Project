package com.TDDev.Spring.Boot.Project.controller;

import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.service.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {
    StorageService storageService;

    @PostMapping("/upload")
    public ApiResponse<?> uploadImage(@RequestParam("image")MultipartFile file,
                                      @RequestParam("product_id") long product_id) throws IOException {
        log.info("Uploading image: {}", file.getOriginalFilename());
        log.info("Product ID: {}", product_id);
        return ApiResponse.builder()
                .message(storageService.uploadImage(file, product_id))
                .build();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        byte[] imageData;
        imageData = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/file-system/upload")
    public ApiResponse<String> uploadImageToFileSystem(@RequestParam("image")MultipartFile file,
                                                  @RequestParam("product_id") long product_id)
            throws IOException {
        log.info("Uploading image: {}", file.getOriginalFilename());
        log.info("Product ID: {}", product_id);
        return ApiResponse.<String>builder()
                .message("Upload successful!")
                .result(storageService.uploadImageToFileSystem(file, product_id))
                .build();
    }

    @GetMapping("/static/{fileName}")
    public ResponseEntity<byte[]> downloadImageFromFileSystem(@PathVariable String fileName)
            throws IOException {
        byte[] imageData;
        imageData = storageService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
