package com.alugai.alugaai.repository;

import com.alugai.alugaai.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByName(String name);
}
