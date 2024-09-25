package com.TDDev.Spring.Boot.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TDDev.Spring.Boot.Project.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
