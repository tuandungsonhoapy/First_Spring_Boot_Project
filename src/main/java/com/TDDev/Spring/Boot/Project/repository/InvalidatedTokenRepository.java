package com.TDDev.Spring.Boot.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TDDev.Spring.Boot.Project.entity.InvalidatedToken;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
