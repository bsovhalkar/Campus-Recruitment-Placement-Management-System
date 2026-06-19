package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.StudentDTO;
import com.spring.placement_management_system.dto.request.ScheduleAssessmentRequestDTO;
import com.spring.placement_management_system.dto.request.UploadScoreRequestDTO;
import com.spring.placement_management_system.dto.response.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    List<AssessmentStudentDTO> getStudentsByAssessmentId(Long assessmentId);

    void uploadScoresCsv(
            Long assessmentId,
            MultipartFile file
    ) throws IOException;

    Resource downloadTemplate(
            Long assessmentId
    ) throws IOException;
}