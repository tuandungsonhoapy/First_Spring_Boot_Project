package com.TDDev.Spring.Boot.Project.controller;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserUpdateRequest;
import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.dto.response.UserResponse;
import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/create-user")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        apiResponse.setMessage("Create user successful!");
        return apiResponse;
    }

    @GetMapping("/users")
    ApiResponse<List<UserResponse>> getUsers(){
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get users successful!");
        apiResponse.setResult(userService.getUsers());
        return apiResponse;
    }

    @GetMapping("/users/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get user successful!");
        apiResponse.setResult(userService.getUser(userId));
        return apiResponse;
    }

    @PutMapping("/users/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Update user successful!");
        apiResponse.setResult(userService.updateUser(userId, request));
        return apiResponse;
    }

    @DeleteMapping("/users/{userId}")
    ApiResponse deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Delete user successful!");
        return apiResponse;
    }
}
