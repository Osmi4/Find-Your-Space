package com.example.backend.service;

import com.example.backend.entity.Permission;
import com.example.backend.entity.Space;
import com.example.backend.enums.PermissionType;
import com.example.backend.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

public interface PermissionService {


    Permission savePermission(Permission permission);

    List<Permission> getAllPermissions();

    List<Permission> getPermissionsForResource(String resourceName, String resourceId);

    void deletePermission(String id);

    Permission createPermission(String username, String resourceName, String resourceId, PermissionType permissionType);

    void checkPermission(String username, String resourceName, String resourceId, PermissionType requiredPermission) throws AccessDeniedException;


    List<String> filterResourceIdsByPermission(List<String> resourceIds, String username, String resourceName, PermissionType requiredPermission);
}