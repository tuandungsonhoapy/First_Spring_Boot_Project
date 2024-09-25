package com.TDDev.Spring.Boot.Project.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserUpdateRequest;
import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.dto.response.UserResponse;
import com.TDDev.Spring.Boot.Project.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/create-user")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        apiResponse.setMessage("Create user successful!");
        return apiResponse;
    }

    @GetMapping("/users")
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get users successful!");
        apiResponse.setResult(userService.getUsers());
        return apiResponse;
    }

    @GetMapping("/users/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get user successful!");
        apiResponse.setResult(userService.getUser(userId));
        return apiResponse;
    }

    @PutMapping("/users/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Update user successful!");
        apiResponse.setResult(userService.updateUser(userId, request));
        return apiResponse;
    }

    @DeleteMapping("/users/{userId}")
    ApiResponse deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Delete user successful!");
        return apiResponse;
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .message("Get your info successful!")
                .result(userService.getMyInfo())
                .build();
    }
}
