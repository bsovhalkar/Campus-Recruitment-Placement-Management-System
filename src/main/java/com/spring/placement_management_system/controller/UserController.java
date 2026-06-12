package com.spring.placement_management_system.controller;

import com.spring.placement_management_system.dto.UserDTO;
import com.spring.placement_management_system.dto.request.RegisterRequest;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.exception.UserException;
import com.spring.placement_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateUser(

            @RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "User updated successfully",
                        userService.updateCurrentUser(userDTO)
                )
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse> deleteUser() {

        userService.deleteCurrentUser();
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "User deleted successfully"
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> me() throws UserException {
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Current user fetched successfully",
                        userService.getCurrentUser()
                )
        );
    }
}