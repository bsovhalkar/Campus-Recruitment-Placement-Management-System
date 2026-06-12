package com.spring.placement_management_system.controller.admin;

import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
public class AdminAnalyticsController {

    private final AnalyticsService analyticsService;

    /**
     * Dashboard Analytics
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse>
    getDashboardAnalytics() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Dashboard analytics fetched successfully",
                        analyticsService.getDashboardAnalytics()
                )
        );
    }

    /**
     * Department Wise Placement
     */
    @GetMapping("/departments")
    public ResponseEntity<ApiResponse>
    getDepartmentWisePlacement() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Department wise placement analytics fetched successfully",
                        analyticsService.getDepartmentWisePlacement()
                )
        );
    }

    /**
     * Selected Students
     */
    @GetMapping("/selected")
    public ResponseEntity<ApiResponse>
    getSelectedStudents() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Selected students analytics fetched successfully",
                        analyticsService.getSelectedStudents()
                )
        );
    }

    /**
     * Rejected Students
     */
    @GetMapping("/rejected")
    public ResponseEntity<ApiResponse>
    getRejectedStudents() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Rejected students analytics fetched successfully",
                        analyticsService.getRejectedStudents()
                )
        );
    }
}