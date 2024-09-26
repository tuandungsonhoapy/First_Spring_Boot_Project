package com.TDDev.Spring.Boot.Project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.TDDev.Spring.Boot.Project.dto.request.RoleRequest.RoleRequest;
import com.TDDev.Spring.Boot.Project.dto.response.RoleResponse;
import com.TDDev.Spring.Boot.Project.entity.Role;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    void updateRole(@MappingTarget Role role, RoleRequest request);
}
