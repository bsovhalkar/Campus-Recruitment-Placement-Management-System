package com.spring.placement_management_system.dto.mapper;

import com.spring.placement_management_system.dto.UserDTO;
import com.spring.placement_management_system.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {

        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();

        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(String.valueOf(user.getRole()));
        dto.setStatus(user.getStatus());

        return dto;
    }
}