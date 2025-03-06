package com.fighthub.fighthubapi.role;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.role.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    public Long saveRole(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        return roleRepository.save(role).getId();
    }

    public RoleResponse findRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .map(roleMapper::toRoleResponse)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
    }

    public PageResponse<RoleResponse> findAllRoles(Integer page, Integer size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Page<Role> roles = roleRepository.findAll(pageable);
        List<RoleResponse> roleResponse = roles.stream()
                .map(roleMapper::toRoleResponse)
                .toList();
        return new PageResponse<>(
                roleResponse,
                roles.getNumber(),
                roles.getSize(),
                roles.getTotalElements(),
                roles.getTotalPages(),
                roles.isFirst(),
                roles.isLast());
    }
    public RoleResponse updateRole(Long roleId, RoleRequest request) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
        role.setName(request.name());
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}
