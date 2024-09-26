package com.TDDev.Spring.Boot.Project.service;

import com.TDDev.Spring.Boot.Project.entity.ImageData;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.repository.StorageRepository;
import com.TDDev.Spring.Boot.Project.util.ImageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StorageService {
    StorageRepository storageRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        storageRepository.save(ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .data(ImageUtils.compressImage(file.getBytes()))
                .build());
        return "File uploaded successfully! File: " + file.getOriginalFilename();
    }

    public byte[] downloadImage(String fileName){
        ImageData imageData = storageRepository.findByName(fileName)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXIST));
        return ImageUtils.decompressImage(imageData.getData());
    }
}
