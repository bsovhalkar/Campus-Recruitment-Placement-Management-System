package com.spring.placement_management_system.controller;

//import com.spring.placement_management_system.dto.response.AssessmentResponseDTO;

import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.dto.response.AssessmentDashboardDTO;
import com.spring.placement_management_system.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;


    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyAssessments(){
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Assessments fetched successfully",
                        assessmentService.getMyAssessments()
                )
        );
    }
    @GetMapping("/{assessmentId}")
    public ResponseEntity<ApiResponse> getAssessment(@PathVariable Long assessmentId){
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Assessment fetched successfully",
                        assessmentService.getAssessmentById(assessmentId)
                )
        );
    }


    @GetMapping("/{assessmentId}/score")
    public ResponseEntity<ApiResponse>
    getAssessmentScore(
            @PathVariable Long assessmentId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Your Assessment score for assessmentId " + assessmentId + " fetched successfully",
                        assessmentService.getAssessmentScore(assessmentId)
                )
        );
    }

//    @GetMapping("/{assessmentId}/eligible")
//    public ResponseEntity<Boolean>
//    isEligibleForInterview(
//            @PathVariable Long assessmentId) {
//
//        return ResponseEntity.ok(
//                assessmentService.isEligibleForInterview(
//                        assessmentId
//                )
//        );
//    }
    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse> getUpcomingAssessments(){
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Your upcoming assessments fetch successfully",
                        assessmentService.getUpcomingAssessments()
                )
        );
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse>
    getAssessmentDashboard() {
        AssessmentDashboardDTO dashboard = assessmentService.getAssessmentDashboard();
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Assessment dashboard fetched successfully",
                        dashboard
                )
        );
    }

}
