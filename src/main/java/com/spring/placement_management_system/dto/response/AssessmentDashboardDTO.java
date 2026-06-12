package com.spring.placement_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDashboardDTO {

    private int totalAssessments;

    private int completed;

    private int upcoming;

//    private int eligibleForInterview;
}