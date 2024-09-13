package com.TDDev.Spring.Boot.Project.service;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserUpdateRequest;
import com.TDDev.Spring.Boot.Project.dto.response.UserResponse;
import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.mapper.UserMapper;
import com.TDDev.Spring.Boot.Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers(){
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userRepository.findAll()){
            userResponseList.add(userMapper.toUserResponse(user));
        }
        return userResponseList;
    }

    public UserResponse getUser(String userId){
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
