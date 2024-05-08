package com.example.backend.service.impl;

import com.example.backend.entity.Permission;
import com.example.backend.entity.Space;
import com.example.backend.enums.PermissionType;
import com.example.backend.repository.PermissionRepository;
import com.example.backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    // In PermissionService.java

    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public List<Permission> getPermissionsForResource(String resourceName, String resourceId) {
        return permissionRepository.findByResourceNameAndResourceId(resourceName, resourceId);
    }

    public void deletePermission(String id) {
        permissionRepository.deleteById(id);
    }
    public Permission createPermission(String username, String resourceName, String resourceId, PermissionType permissionType) {
        Permission permission = new Permission(username, resourceName, resourceId, permissionType);
        return permissionRepository.save(permission);
    }


    public void checkPermission(String username, String resourceName, String resourceId, PermissionType requiredPermission) throws AccessDeniedException {
        List<Permission> permissions = permissionRepository.findByResourceNameAndResourceId(resourceName, resourceId);
        boolean hasPermission = permissions.stream()
                .anyMatch(p -> p.getUsername().equals(username) && p.getPermissionType() == requiredPermission);

        if (!hasPermission) {
            throw new AccessDeniedException("User does not have permission to access this resource.");
        }
    }

    public List<String> filterResourceIdsByPermission(List<String> resourceIds, String username, String resourceName, PermissionType requiredPermission) {
        return resourceIds.stream()
                .filter(resourceId -> {
                    List<Permission> permissions = permissionRepository.findByResourceNameAndResourceId(resourceName, resourceId);
                    return permissions.stream()
                            .anyMatch(p -> p.getUsername().equals(username) && p.getPermissionType() == requiredPermission);
                })
                .collect(Collectors.toList());
    }
}