package com.spring.placement_management_system.controller.admin;

import com.spring.placement_management_system.dto.JobDTO;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.JobStatsDTO;
import com.spring.placement_management_system.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/jobs")
@RequiredArgsConstructor
public class AdminJobController {

    private final JobService jobService;

    /**
     * Create Job
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createJob(
            @RequestBody JobDTO jobDto) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Job created successfully",
                        jobService.createJob(jobDto)
                )
        );
    }

    /**
     * Get All Jobs
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllJobs() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Jobs fetched successfully",
                        jobService.getAllJobs()
                )
        );
    }

    /**
     * Get Job Details
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
     * Get Jobs By Company
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
     * Update Job
     */
    @PutMapping("/{jobId}")
    public ResponseEntity<ApiResponse> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobDTO jobDto) {
//        System.out.println("call to controller-----------------------------------------------------------------------------------");
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Job updated successfully",
                        jobService.updateJob(jobId, jobDto)
                )
        );
    }

    /**
     * Delete Job
     */
    @DeleteMapping("/{jobId}")
    public ResponseEntity<ApiResponse> deleteJob(
            @PathVariable Long jobId) {

        jobService.deleteJob(jobId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Job deleted successfully"
                )
        );
    }

    @GetMapping("/{jobId}/stats")
    public ResponseEntity<ApiResponse> getJobStats(
            @PathVariable Long jobId) {

        JobStatsDTO stats = jobService.getJobStats(jobId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Job stats fetched successfully",
                        stats
                )
        );
    }
}