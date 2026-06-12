package com.spring.placement_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardAnalyticsDTO {

    private Long totalStudents;

    private Long totalCompanies;

    private Long totalJobs;

    private Long totalApplications;

    private Long totalAssessments;

    private Long selectedStudents;

    private Long rejectedStudents;

    private Double placementPercentage;
}