package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.UserDTO;
import com.spring.placement_management_system.dto.request.RegisterRequest;
import com.spring.placement_management_system.exception.UserException;

import java.util.List;

public interface UserService {

//    UserDTO register(RegisterRequest request);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long userId, UserDTO userDTO);

    UserDTO updateCurrentUser(UserDTO userDTO) throws UserException;


    void deleteCurrentUser();


    public CurrentUser getCurrentUser() throws UserException;
}