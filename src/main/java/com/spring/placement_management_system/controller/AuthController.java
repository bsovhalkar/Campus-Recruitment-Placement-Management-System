package com.spring.placement_management_system.controller;

import com.spring.placement_management_system.dto.UserDTO;
import com.spring.placement_management_system.dto.request.LoginRequest;
import com.spring.placement_management_system.dto.request.RegisterRequest;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.LoginResponse;
import com.spring.placement_management_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request){

        UserDTO dto = authService.register(request);
        return new ResponseEntity<>(new ApiResponse(true,"Registration Successfully",dto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @Valid @RequestBody LoginRequest request){

        LoginResponse res = authService.login(request);
        return new ResponseEntity<>(new ApiResponse(true,"Login Successfully",res), HttpStatus.OK);
    }
}