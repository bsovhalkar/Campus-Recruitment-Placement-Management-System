package com.spring.placement_management_system.dto.response;

import com.spring.placement_management_system.constant.AssessmentStatus;
import com.spring.placement_management_system.constant.AssessmentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentAdminResponseDTO {

    private Long assessmentId;

    private Long jobId;

    private String jobTitle;

    private String jobDescription;

    private Double packageOffered;

    private String companyName;

    private String assessmentName;

    private AssessmentType assessmentType;

    private LocalDate assessmentDate;

    private LocalTime assessmentTime;

    private Integer totalMarks;

    private Double interviewPercentage;

    private AssessmentStatus status;
}