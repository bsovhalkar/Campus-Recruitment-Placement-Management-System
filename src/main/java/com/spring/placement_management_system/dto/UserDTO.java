package com.spring.placement_management_system.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String name;
    private String email;
    private String role;
    private String status;
}