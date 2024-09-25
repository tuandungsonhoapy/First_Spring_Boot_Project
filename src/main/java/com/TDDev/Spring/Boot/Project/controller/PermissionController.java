package com.TDDev.Spring.Boot.Project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.TDDev.Spring.Boot.Project.dto.request.PermissionRequest.PermissionRequest;
import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.dto.response.PermissionResponse;
import com.TDDev.Spring.Boot.Project.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/create")
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .message("Create permission successful!")
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .message("Get permissions successful!")
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/delete/{permission}")
    ApiResponse<?> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.builder().message("Delete permission successful!").build();
    }
}
