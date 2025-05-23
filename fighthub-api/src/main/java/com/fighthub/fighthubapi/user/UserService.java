package com.fighthub.fighthubapi.user;

import com.fighthub.fighthubapi.auth.AuthenticationService;
import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final FighterProfileService fighterProfileService;
    private final FighterProfileRepository fighterProfileRepository;
    private final AuthenticationService authenticationService;
    private final TokenRepository tokenRepository;


    public Long saveUser(UserRequest request) throws MessagingException {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .isAccountEnabled(request.isAccountEnabled())
                .isAccountLocked(request.isAccountLocked())
                .roles(request.roles())
                .build();
        Long newUserId = userRepository.save(user).getId();
        FighterProfile newUserProfile = FighterProfile.builder()
                .user(user)
                .createdBy(user.getEmail())
                .build();
        fighterProfileRepository.save(newUserProfile);
        if (!user.isAccountEnabled()) {
            authenticationService.sendValidationEmail(user);
        }
        return newUserId;
    }

    public UserResponse findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("user not found with id: " + userId));
    }

    public PageResponse<UserResponse> findAllUsers(Integer page, Integer size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> userResponse = users.stream()
                .map(userMapper::toUserResponse)
                .toList();
        return new PageResponse<>(
                userResponse,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast());
    }

    public UserResponse updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found with id: " + userId));
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setAccountLocked(request.isAccountLocked());
        user.setAccountEnabled(request.isAccountEnabled());
        Optional.ofNullable(request.roles())
                .filter(roles -> !roles.isEmpty())
                .ifPresent(user::setRoles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("user not found with id: " + userId);
        }
        fighterProfileService.deleteFighterProfile(userId);
        tokenRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }
}
