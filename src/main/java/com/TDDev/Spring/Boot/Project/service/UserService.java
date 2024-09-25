package com.TDDev.Spring.Boot.Project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserUpdateRequest;
import com.TDDev.Spring.Boot.Project.dto.response.UserResponse;
import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.mapper.UserMapper;
import com.TDDev.Spring.Boot.Project.repository.RoleRepository;
import com.TDDev.Spring.Boot.Project.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //        HashSet<String> roles = new HashSet<>();
        //        roles.add(Role.USER.name());

        // user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userResponseList.add(userMapper.toUserResponse(user));
        }
        return userResponseList;
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String userId) {
        //        var authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        //        AtomicBoolean checkAdmin = new AtomicBoolean(false);
        //
        //        authentication.getAuthorities().forEach(grantedAuthority -> {
        //            if(grantedAuthority.getAuthority().equals("ROLE_" + Role.ADMIN.name()))
        //                checkAdmin.set(true);
        //        });
        //
        //        User user = userRepository.findByUsername(authentication.getName())
        //                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        //
        //        log.info(String.valueOf(checkAdmin.get()));
        //        if((!user.getId().equals(userId)) && (!checkAdmin.get()))
        //            throw new AppException(ErrorCode.DO_NOT_PERMISSION);

        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.toUserResponse(userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
