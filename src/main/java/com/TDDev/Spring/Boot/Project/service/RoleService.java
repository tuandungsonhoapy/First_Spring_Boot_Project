package com.TDDev.Spring.Boot.Project.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.TDDev.Spring.Boot.Project.dto.request.RoleRequest.RoleRequest;
import com.TDDev.Spring.Boot.Project.dto.response.RoleResponse;
import com.TDDev.Spring.Boot.Project.entity.Role;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.mapper.RoleMapper;
import com.TDDev.Spring.Boot.Project.repository.PermissionRepository;
import com.TDDev.Spring.Boot.Project.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse create(RoleRequest request) {
        if (roleRepository.findById(request.getName()).isPresent()) throw new AppException(ErrorCode.ROLE_EXISTED);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        Role role = roleMapper.toRole(request);
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();

        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String role) {
        roleRepository.deleteById(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse update(RoleRequest request) {
        var role = roleRepository
                .findById(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        roleMapper.updateRole(role, request);

        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
}
