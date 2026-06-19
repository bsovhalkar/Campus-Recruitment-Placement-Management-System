//
//package com.spring.placement_management_system.controller;
//import com.spring.placement_management_system.dto.request.CreateStudentRequest;
//import com.spring.placement_management_system.dto.response.ApiResponse;
//import com.spring.placement_management_system.dto.StudentDTO;
//import com.spring.placement_management_system.dto.response.MyProfileResponse;
//import com.spring.placement_management_system.service.StudentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/students")
//@RequiredArgsConstructor
//public class StudentController {
//
//    private final StudentService studentService;
//
//    @PostMapping
//    public ResponseEntity<ApiResponse> createProfile(
//            @RequestBody CreateStudentRequest request) {
//
//        StudentDTO student = studentService.createProfile(request);
//
//        return ResponseEntity.ok(
//                new ApiResponse(
//                        true,
//                        "Student profile created successfully",
//                        student
//                )
//        );
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse> getProfile(
//            @PathVariable Long id) {
//
//        StudentDTO student = studentService.getProfile(id);
//
//        return ResponseEntity.ok(
//                new ApiResponse(
//                        true,
//                        "Student profile fetched successfully",
//                        student
//                )
//        );
//    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse> updateProfile(
//            @PathVariable Long id,
//            @RequestBody CreateStudentRequest request) {
//
//        StudentDTO student =
//                studentService.updateProfile(id, request);
//
//        return ResponseEntity.ok(
//                new ApiResponse(
//                        true,
//                        "Student profile updated successfully",
//                        student
//                )
//        );
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse> deleteProfile(
//            @PathVariable Long id) {
//
//        studentService.deleteProfile(id);
//
//        return ResponseEntity.ok(
//                new ApiResponse(
//                        true,
//                        "Student deleted successfully"
//                )
//        );
//    }
//    @GetMapping("/me")
//    public ResponseEntity<MyProfileResponse> getMyProfile() {
//        return ResponseEntity.ok(studentService.getMyProfile());
//    }
//}


package com.spring.placement_management_system.controller;

import com.spring.placement_management_system.dto.StudentDTO;
import com.spring.placement_management_system.dto.request.CreateStudentRequest;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.MyProfileResponse;
import com.spring.placement_management_system.dto.response.ProfileCompletionResponse;
import com.spring.placement_management_system.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * Create Student Profile
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createProfile(
            @Valid @RequestBody CreateStudentRequest request) {

        StudentDTO student =
                studentService.createProfile(request);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Student profile created successfully",
                        student
                )
        );
    }

    /**
     * Get Logged In Student Profile
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyProfile() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Profile fetched successfully",
                        studentService.getMyProfile()
                )
        );
    }

    /**
     * Update Logged In Student Profile
     */
    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateMyProfile(
            @Valid @RequestBody CreateStudentRequest request) {

        StudentDTO student =
                studentService.updateMyProfile(request);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Profile updated successfully",
                        student
                )
        );
    }

    /**
     * Delete Logged In Student Profile
     */
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse> deleteMyProfile() {

        studentService.deleteMyProfile();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Profile deleted successfully"
                )
        );
    }

    /**
     * Check Profile Completion Status
     */
    @GetMapping("/profile-status")
    public ResponseEntity<ApiResponse> getProfileStatus() {

        ProfileCompletionResponse completed =
                studentService.isMyProfileCompleted();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Profile completion status fetched successfully",
                        completed
                )
        );

    }
    @GetMapping("/have-student-profile")
    public ResponseEntity<ApiResponse> getHaveStudentProfile() {
        boolean is = studentService.doIHaveStudentProfile();
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Have student profile fetched successfully",
                        is
                )
        );
    }
}