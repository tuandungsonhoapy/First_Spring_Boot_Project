package com.TDDev.Spring.Boot.Project.controller;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserUpdateRequest;
import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        apiResponse.setMessage("Create user successful!");
        return apiResponse;
    }

    @GetMapping("/users")
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    User getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/users/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/users/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "user has been deleted!";
    }
}
