package com.TDDev.Spring.Boot.Project.repository;

import com.TDDev.Spring.Boot.Project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
