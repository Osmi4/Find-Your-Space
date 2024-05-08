package com.example.backend.entity;

import com.example.backend.enums.PermissionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Entity@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String resourceName; // This could be renamed to better reflect it's more of a resource type
    private String resourceId;    // Specific identifier for the resource
    private PermissionType permissionType;

    // Constructors, Getters, and Setters
    public Permission() {}

    public Permission(String username, String resourceName, String resourceId, PermissionType permissionType) {
        this.username = username;
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.permissionType = permissionType;
    }

    // standard getters and setters
}