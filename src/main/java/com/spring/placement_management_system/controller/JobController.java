package com.spring.placement_management_system.controller;

import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.JobDTO;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.ApplicationService;
import com.spring.placement_management_system.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final ApplicationService applicationService;
    /**
     * All Available Jobs
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllJobs() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Jobs fetched successfully",
                        jobService.getAllAvailableJobs()
                )
        );
    }

    /**
     * Job Details
     */
    @GetMapping("/{jobId}")
    public ResponseEntity<ApiResponse> getJobById(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Job fetched successfully",
                        jobService.getJobById(jobId)
                )
        );
    }

    /**
     * Jobs By Company
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse> getJobsByCompany(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Company jobs fetched successfully",
                        jobService.getJobsByCompany(companyId)
                )
        );
    }

    /**
     * Eligible Jobs
     */
    @GetMapping("/eligible")
    public ResponseEntity<ApiResponse> getEligibleJobs() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Eligible jobs fetched successfully",
                        jobService.getEligibleJobs()
                )
        );
    }

//    @GetMapping("/{jobId}/status")
//    public ResponseEntity<StatusConstants> getApplicationStatus(
//            @PathVariable Long jobId) {
//
//        return ResponseEntity.ok(
//                applicationService.getApplicationStatus(jobId)
//        );
//    }

}