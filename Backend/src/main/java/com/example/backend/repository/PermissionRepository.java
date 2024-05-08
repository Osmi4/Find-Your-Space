package com.example.backend.repository;

import com.example.backend.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    List<Permission> findByResourceNameAndResourceId(String resourceName, String resourceId);
}