package com.spring.placement_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrentUser {

    private Long userId;
    private String email;
    private String role;
}