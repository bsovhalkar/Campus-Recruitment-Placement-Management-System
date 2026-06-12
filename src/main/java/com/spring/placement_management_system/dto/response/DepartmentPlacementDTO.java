package com.spring.placement_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentPlacementDTO {

    private String department;

    private Long totalStudents;

    private Long selectedStudents;

    private Double placementPercentage;
}