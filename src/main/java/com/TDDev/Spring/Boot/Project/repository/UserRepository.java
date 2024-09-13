package com.TDDev.Spring.Boot.Project.repository;

import com.TDDev.Spring.Boot.Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
}
