package com.spring.placement_management_system.dto.mapper;


import com.spring.placement_management_system.dto.response.AssessmentAdminResponseDTO;
import com.spring.placement_management_system.dto.response.AssessmentResponseDTO;
import com.spring.placement_management_system.entity.Assessment;
import com.spring.placement_management_system.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class AssessmentMapper {

    public AssessmentResponseDTO mapToResponse(
            Assessment assessment,
            Job job,
            Double score) {

        return AssessmentResponseDTO.builder()
                .CompanyName(job.getCompany().getCompanyName())
                .jobName(job.getTitle())
                .jobDescription(job.getDescription())
                .assessmentName(assessment.getAssessmentName())
                .assessmentType(assessment.getAssessmentType())
                .assessmentStatus(assessment.getStatus())
                .assessmentDate(assessment.getAssessmentDate())
                .score(score)
                .build();
    }

    public AssessmentAdminResponseDTO  mapToAdminResponse(Assessment assessment,Job job) {
        return AssessmentAdminResponseDTO.builder()
                .assessmentId(assessment.getAssessmentId())
                .jobId(assessment.getJobId())
                .jobTitle(job.getTitle())
                .jobDescription(job.getDescription())
                .packageOffered(job.getPackageOffered())
                .companyName(job.getCompany().getCompanyName())
                .assessmentName(assessment.getAssessmentName())
                .assessmentType(assessment.getAssessmentType())
                .assessmentDate(assessment.getAssessmentDate())
                .assessmentTime(assessment.getAssessmentTime())
                .totalMarks(assessment.getTotalMarks())
                .interviewPercentage(assessment.getInterviewPercentage())
                .status(assessment.getStatus())
                .build();
    }
}
