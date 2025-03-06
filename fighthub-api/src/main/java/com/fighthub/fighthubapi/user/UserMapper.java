package com.fighthub.fighthubapi.user;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import com.fighthub.fighthubapi.role.Role;

@Service
public class UserMapper {

    public User toUser(UserRequest request) {
        return User.builder()
                .id(request.id())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .isAccountLocked(request.isAccountLocked())
                .isAccountEnabled(request.isAccountEnabled())
                .roles(request.roles())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getNickname())
                .email(user.getEmail())
                .isAccountLocked(user.isAccountLocked())
                .isAccountEnabled(user.isAccountEnabled())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }
}
