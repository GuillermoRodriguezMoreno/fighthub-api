package com.fighthub.fighthubapi.user;

import com.fighthub.fighthubapi.role.Role;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(Optional.ofNullable(user.getUpdatedAt())
                        .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .orElse(null))
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }
}
