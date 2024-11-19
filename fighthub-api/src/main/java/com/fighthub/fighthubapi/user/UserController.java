package com.fighthub.fighthubapi.user;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> saveUser(
            @Valid @RequestBody UserRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(userService.saveUser(request));
    }
    @GetMapping("{user-id}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable("user-id") Long userId
    ) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> findAllUsers(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ){
        return ResponseEntity.ok(userService.findAllUsers(page, size));
    }
    @PutMapping("{user-id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable("user-id") Long userId,
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
    @DeleteMapping("{user-id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("user-id") Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
