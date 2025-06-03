package com.fighthub.fighthubapi.role;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.role.RoleRequest;
import com.fighthub.fighthubapi.role.RoleResponse;
import com.fighthub.fighthubapi.role.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Roles")
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Long> saveRole(
            @Valid @RequestBody RoleRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(roleService.saveRole(request));
    }
    @GetMapping("{role-id}")
    public ResponseEntity<RoleResponse> findRoleById(
            @PathVariable("role-id") Long roleId
    ) {
        return ResponseEntity.ok(roleService.findRoleById(roleId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<RoleResponse>> findAllRoles(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "name", required = false) String orderBy
    ){
        return ResponseEntity.ok(roleService.findAllRoles(page, size, orderBy));
    }
    @PutMapping("{role-id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable("role-id") Long roleId,
            @Valid @RequestBody RoleRequest request
    ) {
        return ResponseEntity.ok(roleService.updateRole(roleId, request));
    }
    @DeleteMapping("{role-id}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable("role-id") Long roleId
    ) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}
