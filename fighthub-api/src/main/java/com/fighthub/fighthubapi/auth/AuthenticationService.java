package com.fighthub.fighthubapi.auth;

import com.fighthub.fighthubapi.email.EmailService;
import com.fighthub.fighthubapi.email.EmailTemplateName;
import com.fighthub.fighthubapi.role.Role;
import com.fighthub.fighthubapi.role.RoleRepository;
import com.fighthub.fighthubapi.user.Token;
import com.fighthub.fighthubapi.user.TokenRepository;
import com.fighthub.fighthubapi.user.User;
import com.fighthub.fighthubapi.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Value("${application.email.activation-url}")
    private String activationUrl;

    public void register(ResgistrationRequest request) throws MessagingException {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountEnabled(false)
                .isAccountLocked(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
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
}
