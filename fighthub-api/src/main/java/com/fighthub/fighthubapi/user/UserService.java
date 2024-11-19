package com.fighthub.fighthubapi.user;

import com.fighthub.fighthubapi.user.*;
import com.fighthub.fighthubapi.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Long saveUser(UserRequest request) {
        User user = userMapper.toUser(request);
        return userRepository.save(user).getId();
    }

    public UserResponse findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("user not found with id: " + userId));
    }

    public PageResponse<UserResponse> findAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstname", "lastname").descending());
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
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setUsername(request.username());
        user.setDateOfBirth(request.dateOfBirth());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setAccountLocked(request.isAccountLocked());
        user.setAccountEnabled(request.isAccountEnabled());
        user.setRoles(request.roles());
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
