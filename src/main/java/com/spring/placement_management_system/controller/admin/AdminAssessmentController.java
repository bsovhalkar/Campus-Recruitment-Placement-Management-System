package com.spring.placement_management_system.controller.admin;

import com.spring.placement_management_system.dto.request.ScheduleAssessmentRequestDTO;
import com.spring.placement_management_system.dto.request.UploadScoreRequestDTO;
import com.spring.placement_management_system.dto.response.ApiResponse;
import com.spring.placement_management_system.service.AssessmentService;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/assessments")
@RequiredArgsConstructor
public class AdminAssessmentController {

    private final AssessmentService assessmentService;

    /**
     * Schedule Assessment
     */
    @PostMapping
    public ResponseEntity<ApiResponse>
    scheduleAssessment(
            @Valid
            @RequestBody
            ScheduleAssessmentRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(
                        true,
                        "Assessment scheduled successfully",
                        assessmentService.scheduleAssessment(request)
                )
        );
    }

    /**
     * All Assessments
     */
    @GetMapping
    public ResponseEntity<ApiResponse>
    getAllAssessments() {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Assessments fetched successfully",
                        assessmentService.adminGetAllAssessments()
                )
        );
    }

    /**
     * Assessment Details
     */
    @GetMapping("/{assessmentId}")
    public ResponseEntity<ApiResponse>
    getAssessmentById(
            @PathVariable Long assessmentId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Assessment fetched successfully",
                        assessmentService.getAssessmentByAssessmentId(assessmentId)
                )
        );
    }

//    @GetMapping("/{assessmentId}/scores")
//    public ResponseEntity<ApiResponse>
//    getAssessmentScores(
//            @PathVariable Long assessmentId
//    ){
//        return ResponseEntity.ok(
//                new ApiResponse(
//                        true,
//                        "Score fetched successfully",
//                        assessmentService.getScoresByAssessmentId(assessmentId)
//                )
//        );
//    }

    @GetMapping("/{assessmentId}/students")
    public ResponseEntity<ApiResponse>
    getStudentsByAssessmentId(
            @PathVariable Long assessmentId
    ){
        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "students fetched successfully for assessmentId " + assessmentId,
                        assessmentService.getStudentsByAssessmentId(assessmentId)
                )
        );
    }

    /**
     * Assessments By Job
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse>
    getAssessmentsByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Assessments fetched successfully",
                        assessmentService.adminGetAllAssessmentsByJobId(jobId)
                )
        );
    }

    /**
     * Upload Scores
     */
    @PostMapping("/{assessmentId}/scores")
    public ResponseEntity<ApiResponse>
    uploadScores(
            @PathVariable Long assessmentId,
            @RequestBody
            List<UploadScoreRequestDTO> scores) {

        assessmentService.uploadScores(
                assessmentId,
                scores
        );

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Scores uploaded successfully"
                )
        );
    }

    /**
     * Delete Assessment
     */
    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<ApiResponse>
    deleteAssessment(
            @PathVariable Long assessmentId) {

        assessmentService.deleteAssessment(
                assessmentId
        );

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        "Assessment deleted successfully"
                )
        );
    }
    @GetMapping("/{assessmentId}/template")
    public ResponseEntity<Resource> downloadTemplate(
            @PathVariable Long assessmentId
    ) throws IOException {

        Resource resource =
                assessmentService.downloadTemplate(
                        assessmentId
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=scores_template.csv"
                )
                .contentType(
                        MediaType.parseMediaType(
                                "text/csv"
                        )
                )
                .body(resource);
    }

    @PostMapping("/{assessmentId}/scores/csv")
    public ResponseEntity<ApiResponse> uploadScoresCsv(
            @PathVariable Long assessmentId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        assessmentService.uploadScoresCsv(
                assessmentId,
                file
        );

        return ResponseEntity.ok(
                new ApiResponse(
                        true,
                        null,
                        "Scores uploaded successfully"
                )
        );
    }
}