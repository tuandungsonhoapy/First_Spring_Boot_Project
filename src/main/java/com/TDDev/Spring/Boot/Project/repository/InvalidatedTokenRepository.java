package com.TDDev.Spring.Boot.Project.repository;

import com.TDDev.Spring.Boot.Project.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
