package com.TDDev.Spring.Boot.Project.repository;

import com.TDDev.Spring.Boot.Project.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
