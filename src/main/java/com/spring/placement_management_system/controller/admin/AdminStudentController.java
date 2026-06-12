package com.spring.placement_management_system.controller.admin;

import com.spring.placement_management_system.dto.StudentDTO;
import com.spring.placement_management_system.dto.request.BulkStudentRequest;
import com.spring.placement_management_system.dto.request.CreateStudentRequest;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@RequiredArgsConstructor
public class AdminStudentController {

    private final StudentService studentService;

    // Get all students
    @GetMapping
    public ResponseEntity<ApiResponse> getAllStudents() {

        List<StudentDTO> students = studentService.getAllStudents();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Students fetched successfully",
                        students
                )
        );
    }

    // Get one student
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentById(
            @PathVariable Long id) {

        StudentDTO student = studentService.getProfile(id);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Student fetched successfully",
                        student
                )
        );
    }

    // Update one student
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody CreateStudentRequest request) {

        StudentDTO student =
                studentService.updateProfile(id, request);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Student updated successfully",
                        student
                )
        );
    }

    // Delete one student
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteProfile(id);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Student deleted successfully"
                )
        );
    }

    // Bulk delete
    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse> bulkDeleteStudents(
            @RequestBody List<Long> studentIds) {

        studentService.bulkDeleteStudents(studentIds);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Students deleted successfully"
                )
        );
    }

    // Bulk update
    @PutMapping("/bulk")
    public ResponseEntity<ApiResponse> bulkUpdateStudents(
            @RequestBody List<BulkStudentRequest> requests) {

        List<StudentDTO> updatedStudents =
                studentService.bulkUpdateStudents(requests);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Students updated successfully",
                        updatedStudents
                )
        );
    }

    @PostMapping("/bulk/create")
    public ResponseEntity<ApiResponse> bulkStudentProfile(@RequestBody List<CreateStudentRequest> requests) {
        for (CreateStudentRequest request : requests) {
            studentService.createProfile(request);
        }
        return ResponseEntity.ok(
                new ApiResponse(true, "Student profile created successfully")
        );
    }

}