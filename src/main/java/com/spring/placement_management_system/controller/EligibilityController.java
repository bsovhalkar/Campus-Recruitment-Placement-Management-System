package com.spring.placement_management_system.controller;


import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EligibilityController {

    private final EligibilityService eligibilityService;

    @GetMapping("/jobs/{jobId}/eligibility/{studentId}")
    public ResponseEntity<ApiResponse> checkEligibility(
            @PathVariable Long jobId,
            @PathVariable Long studentId) {

        boolean eligible =
                eligibilityService.isEligible(studentId, jobId);

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        eligible
                                ? "Student is eligible"
                                : "Student is not eligible",
                        eligible
                )
        );
    }
}