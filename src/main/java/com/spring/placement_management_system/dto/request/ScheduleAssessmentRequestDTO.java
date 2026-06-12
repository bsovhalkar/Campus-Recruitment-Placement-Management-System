package com.spring.placement_management_system.dto.request;

import com.spring.placement_management_system.constant.AssessmentType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleAssessmentRequestDTO {

    @NotNull
    private Long jobId;

    @NotBlank
    private String assessmentName;

    @NotNull
    private AssessmentType assessmentType;

    @NotNull
    private LocalDate assessmentDate;

    @NotNull
    private LocalTime assessmentTime;

    @Min(1)
    private Integer totalMarks;

    @Min(1)
    @Max(100)
    private Double interviewPercentage;
}