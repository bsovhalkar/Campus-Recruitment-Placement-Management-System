package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.request.ScheduleAssessmentRequestDTO;
import com.spring.placement_management_system.dto.request.UploadScoreRequestDTO;
import com.spring.placement_management_system.dto.response.AssessmentAdminResponseDTO;
import com.spring.placement_management_system.dto.response.AssessmentDashboardDTO;
import com.spring.placement_management_system.dto.response.AssessmentResponseDTO;

import java.util.List;

public interface AssessmentService {

    AssessmentAdminResponseDTO scheduleAssessment(
            ScheduleAssessmentRequestDTO request);

    void uploadScores(
            Long assessmentId,
            List<UploadScoreRequestDTO> scores);
    List<AssessmentResponseDTO> getMyAssessments();

    AssessmentResponseDTO getAssessmentById(Long assessmentId);

    Double getAssessmentScore(Long assessmentId);

    Boolean isEligibleForInterview(Long assessmentId);

    List<AssessmentResponseDTO> getUpcomingAssessments();

    AssessmentDashboardDTO getAssessmentDashboard();

    List<AssessmentResponseDTO> getAssessmentsByJobId(Long jobId);

    List<AssessmentResponseDTO> getAllAssessments();

    void deleteAssessment(Long assessmentId);

    AssessmentAdminResponseDTO getAssessmentByAssessmentId(Long assessmentId);

    List<AssessmentAdminResponseDTO> adminGetAllAssessments();

    List<AssessmentAdminResponseDTO> adminGetAllAssessmentsByJobId(Long jobId);
}