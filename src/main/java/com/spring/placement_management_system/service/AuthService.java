package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.UserDTO;
import com.spring.placement_management_system.dto.request.LoginRequest;
import com.spring.placement_management_system.dto.request.RegisterRequest;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.LoginResponse;

public interface AuthService {

    UserDTO register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}