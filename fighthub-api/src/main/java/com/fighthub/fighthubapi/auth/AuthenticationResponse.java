package com.fighthub.fighthubapi.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private String token;
    private String email;
    private String username;
    private List<String> roles;
}
