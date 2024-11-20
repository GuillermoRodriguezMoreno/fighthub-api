package com.fighthub.fighthubapi.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserRequest request) {
        return User.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .username(request.username())
                .dateOfBirth(request.dateOfBirth())
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
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getNickname())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .isAccountLocked(user.isAccountLocked())
                .isAccountEnabled(user.isAccountEnabled())
                .roles(user.getRoles())
                .fullName(user.getFullName())
                .build();
    }
}
