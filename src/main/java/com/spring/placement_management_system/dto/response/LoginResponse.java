package com.spring.placement_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private Long id;
    private String email;
    private String name;
    private String role;
    private String token;
    private long expiresIn;
}
