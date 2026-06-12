package com.spring.placement_management_system.controller;

import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.ApplicationService;
import com.spring.placement_management_system.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JobService jobService;




    @PostMapping("/{jobId}/apply")
    public ResponseEntity<ApiResponse> applyForJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Applied for job successfully",
                        applicationService.applyForJob(jobId)
                )
        );
    }

    @GetMapping("/{jobId}/status")
    public ResponseEntity<ApiResponse> getApplicationStatus(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Application status fetched successfully",
                        applicationService.getApplicationStatus(jobId)
                )
        );
    }

    @PutMapping("/{applicationId}/withdraw")
    public ResponseEntity<ApiResponse> withdrawApplication(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Application withdrawn successfully",
                        applicationService.withdrawApplication(applicationId)
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse>
    getMyApplications(
            @RequestParam(required = false)
            StatusConstants status) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "My applications fetched successfully",
                        applicationService.getStudentApplications(status)
                )
        );
    }

    @GetMapping("/details/{applicationId}")
    public ResponseEntity<ApiResponse>
    getApplicationById(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Fetched Application with ID " + applicationId,
                        applicationService.getApplicationById(applicationId)
                )
        );
    }
}