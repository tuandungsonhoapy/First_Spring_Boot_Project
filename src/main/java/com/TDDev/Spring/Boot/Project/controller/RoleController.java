package com.TDDev.Spring.Boot.Project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.TDDev.Spring.Boot.Project.dto.request.RoleRequest.RoleRequest;
import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.dto.response.RoleResponse;
import com.TDDev.Spring.Boot.Project.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping("/create")
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .message("Create role successful")
                .result(roleService.create(roleRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .message("Get roles successful!")
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/delete/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().message("Delete role successful!").build();
    }
}
