package com.fighthub.fighthubapi.auth;

import com.fighthub.fighthubapi.email.EmailService;
import com.fighthub.fighthubapi.email.EmailTemplateName;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
import com.fighthub.fighthubapi.role.Role;
import com.fighthub.fighthubapi.role.RoleRepository;
import com.fighthub.fighthubapi.security.JwtService;
import com.fighthub.fighthubapi.user.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FighterProfileRepository fighterProfileRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.email.activation-url}")
    private String activationUrl;

    public UserResponse register(ResgistrationRequest request) throws MessagingException {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountEnabled(true)
                .isAccountLocked(false)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
        FighterProfile newUserProfile = FighterProfile.builder()
                .user(user)
                .createdBy(user.getEmail())
                .build();
        fighterProfileRepository.save(newUserProfile);
        //sendValidationEmail(user);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
        return userResponse;
    }

    public void sendValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getUsername(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate your account"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            codeBuilder.append(chars.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Map<String, Object> claims = new HashMap<>();
        User user = (User) auth.getPrincipal();
        String jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(user.getEmail())
                .isAccountEnabled(user.isAccountEnabled())
                .isAccountLocked(user.isAccountLocked())
                .userId(user.getId())
                .username(user.getNickname())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .toList())
                .build();
    }

    //@Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new IllegalStateException("Token expired. A new token has been sent to your email");
        }
        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setAccountEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
