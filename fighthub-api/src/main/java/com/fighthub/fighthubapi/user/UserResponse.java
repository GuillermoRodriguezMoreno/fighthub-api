package com.fighthub.fighthubapi.user;

import com.fighthub.fighthubapi.club.Club;
import com.fighthub.fighthubapi.role.Role;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private LocalDate dateOfBirth;
    private String email;
    private boolean isAccountLocked;
    private boolean isAccountEnabled;
    private List<Role> roles;
    private String fullName;
}
