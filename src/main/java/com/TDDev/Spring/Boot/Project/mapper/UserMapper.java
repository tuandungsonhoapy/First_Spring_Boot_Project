package com.TDDev.Spring.Boot.Project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserCreationRequest;
import com.TDDev.Spring.Boot.Project.dto.request.UserRequest.UserUpdateRequest;
import com.TDDev.Spring.Boot.Project.dto.response.UserResponse;
import com.TDDev.Spring.Boot.Project.entity.User;

@Mapper(
        componentModel = "spring",
        uses = {RoleMapper.class})
public interface UserMapper {

    User toUser(UserCreationRequest request);

    @Mapping(source = "role", target = "role")
    UserResponse toUserResponse(User user);

    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
