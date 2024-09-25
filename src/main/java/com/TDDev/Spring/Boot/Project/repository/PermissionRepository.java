package com.TDDev.Spring.Boot.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TDDev.Spring.Boot.Project.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
