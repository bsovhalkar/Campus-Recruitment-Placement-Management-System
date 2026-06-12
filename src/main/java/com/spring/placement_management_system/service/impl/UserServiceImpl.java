package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.UserDTO;
import com.spring.placement_management_system.dto.mapper.UserMapper;
import com.spring.placement_management_system.dto.request.RegisterRequest;
import com.spring.placement_management_system.entity.User;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.repository.UserRepository;
import com.spring.placement_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

//    @Override
//    public UserDTO register(RegisterRequest request) {
//
//        if (userRepository.existsByEmail(request.getEmail())) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        User user = User.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(RoleConstants.STUDENT)
//                .status("ACTIVE")
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        user = userRepository.save(user);
//
//        return userMapper.toDTO(user);
//    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        if(userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if(userDTO.getEmail() != null) {
            if (userRepository.existsByEmail(userDTO.getEmail()) &&
                    !Objects.equals(user.getEmail(), userDTO.getEmail())) {
                throw new UserException("Email already exists");
            }
            user.setEmail(userDTO.getEmail());
        }


        if (userDTO.getRole() != null) {
            user.setRole(RoleConstants.valueOf(userDTO.getRole()));
        }


        User updatedUser = userRepository.save(user);

        return userMapper.toDTO(updatedUser);
    }

    @Override
    public UserDTO updateCurrentUser(UserDTO userDTO) throws UserException {
        CurrentUser currentUser = getCurrentUser();
        return updateUser(currentUser.getUserId(), userDTO);
    }

    @Override
    public void deleteCurrentUser() {
        CurrentUser currentUser = getCurrentUser();

        User user = userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new UserException("User not found"));
        user.setStatus("INACTIVE");

        userRepository.save(user);
    }

    @Override
    public CurrentUser getCurrentUser() throws UserException {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof CurrentUser currentUser)) {

            throw new UserException("No authenticated user found");
        }

        User user = userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new UserException("User not found"));
        if (user.getRole() == RoleConstants.STUDENT && user.getStatus().equals("INACTIVE")) {
            throw new UserException("User is inactive");
        }

        return currentUser;
    }



}