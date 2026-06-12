
package com.spring.placement_management_system.controller;

import com.spring.placement_management_system.dto.ResumeDTO;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.entity.Resume;
import com.spring.placement_management_system.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadResume(
            @RequestParam("file") MultipartFile file) {
        ResumeDTO resume =
                resumeService.uploadMyResume(file);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Resume uploaded successfully",
                        resume
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getResume(){

        ResumeDTO resume =
                resumeService.getMyResume();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Resume fetched successfully",
                        resume
                )
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse> deleteResume(
            ) {

        resumeService.deleteMyResume();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Resume deleted successfully"
                )
        );
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse> getResumeStatus(){

        String status = resumeService.status();

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Resume status fetched successfully",
                        status
                )
        );
    }
}