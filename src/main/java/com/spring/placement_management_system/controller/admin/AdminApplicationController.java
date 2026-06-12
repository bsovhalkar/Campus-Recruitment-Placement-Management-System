package com.spring.placement_management_system.controller.admin;

import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.ApplicationResponse;
import com.spring.placement_management_system.service.ApplicationService;
import com.spring.placement_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Component
@RestController
@RequestMapping("/api/admin/applications")
@RequiredArgsConstructor
public class AdminApplicationController {
    private final UserService userService;
    private final ApplicationService applicationService;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllApplications() {
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "applications fetched successfully",
                        applicationService.getAllApplications()
                )
        );
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<ApiResponse> getAllApplications(@PathVariable Long jobId) {
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "job fetched successfully",
                        applicationService.getJobApplications(jobId)
                )
        );
    }

    @GetMapping("/{applicationId}")
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

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApiResponse>
    updateStatus(
            @PathVariable Long applicationId,
            @RequestParam StatusConstants status) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "updated status of application with ID " + applicationId + " to " + status,
                        applicationService.updateApplicationStatus(
                                applicationId,
                                status
                        )
                )
        );

    }

}
