package com.spring.placement_management_system.dto.response;

import com.spring.placement_management_system.constant.AssessmentStatus;
import com.spring.placement_management_system.constant.AssessmentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AssessmentResponseDTO {
    private String CompanyName;
    private String jobName;
    private String jobDescription;
    private String assessmentName;
    private AssessmentType assessmentType;
    private AssessmentStatus assessmentStatus;
    private LocalDate assessmentDate;
    private LocalDateTime assessmentTime;
    private Double score;
}
