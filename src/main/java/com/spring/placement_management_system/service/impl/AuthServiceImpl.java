package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.dto.UserDTO;
import com.spring.placement_management_system.dto.mapper.UserMapper;
import com.spring.placement_management_system.dto.request.LoginRequest;
import com.spring.placement_management_system.dto.request.RegisterRequest;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.LoginResponse;
import com.spring.placement_management_system.entity.User;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.UserRepository;
import com.spring.placement_management_system.security.JwtService;
import com.spring.placement_management_system.service.AuthService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Override
    public UserDTO register(RegisterRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (user != null) {
            if(user.getStatus().equals("INACTIVE")) {
                user.setStatus("ACTIVE");
                user.setName(request.getName());
                user.setPassword(request.getPassword());
                userRepository.save(user);
                return userMapper.toDTO(user);
            }
            else {
                throw new UserException("Email already in use");
            }
        }

        user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(RoleConstants.STUDENT)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);

        return userMapper.toDTO(saved);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Optional<User> user =
                userRepository.findByEmail(request.getEmail());

        if(user.isEmpty()){
            throw  new UserException("User Not Found");
        }

        if(!user.get().getPassword()
                .equals(request.getPassword())){

            throw  new UserException("Invalid Password");
        }
        if(user.get().getStatus().equals("INACTIVE")){
            throw  new UserException("User Not Active");
        }

        // Generate JWT token
        User loggedInUser = user.get();
        String token = jwtService.generateToken(
                loggedInUser.getUserId(),
                loggedInUser.getEmail(),
                loggedInUser.getName(),
                String.valueOf(loggedInUser.getRole())
        );

        // Create login response with token

        return LoginResponse.builder()
                .id(loggedInUser.getUserId())
                .email(loggedInUser.getEmail())
                .name(loggedInUser.getName())
                .role(String.valueOf(loggedInUser.getRole()))
                .token(token)
                .expiresIn(jwtExpirationMs / 1000) // Convert to seconds
                .build();
    }
}

