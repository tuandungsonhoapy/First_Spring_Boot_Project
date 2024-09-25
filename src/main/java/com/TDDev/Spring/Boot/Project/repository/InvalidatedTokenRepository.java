package com.TDDev.Spring.Boot.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TDDev.Spring.Boot.Project.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
