package com.TDDev.Spring.Boot.Project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TDDev.Spring.Boot.Project.dto.request.PermissionRequest.PermissionRequest;
import com.TDDev.Spring.Boot.Project.dto.response.PermissionResponse;
import com.TDDev.Spring.Boot.Project.exception.AppException;
import com.TDDev.Spring.Boot.Project.exception.ErrorCode;
import com.TDDev.Spring.Boot.Project.mapper.PermissionMapper;
import com.TDDev.Spring.Boot.Project.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        var permission = permissionRepository.findById(request.getName());
        if (permission.isPresent()) throw new AppException(ErrorCode.PERMISSION_EXISTED);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permissionMapper.toPermission(request)));
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
