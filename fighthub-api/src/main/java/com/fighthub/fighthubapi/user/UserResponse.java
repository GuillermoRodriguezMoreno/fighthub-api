package com.fighthub.fighthubapi.user;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private boolean isAccountLocked;
    private boolean isAccountEnabled;
    private String createdAt;
    private String updatedAt;
    private Set<String> roles;
}
