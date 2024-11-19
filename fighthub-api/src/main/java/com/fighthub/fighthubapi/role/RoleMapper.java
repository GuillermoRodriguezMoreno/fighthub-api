package com.fighthub.fighthubapi.role;

import org.springframework.stereotype.Service;

@Service
public class RoleMapper {

    public Role toRole(RoleRequest request) {
        return Role.builder()
                .name(request.name())
                .build();
    }

    public RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}
