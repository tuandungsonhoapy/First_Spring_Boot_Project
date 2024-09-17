package com.TDDev.Spring.Boot.Project.mapper;

import com.TDDev.Spring.Boot.Project.dto.request.PermissionRequest.PermissionRequest;
import com.TDDev.Spring.Boot.Project.dto.response.PermissionResponse;
import com.TDDev.Spring.Boot.Project.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
