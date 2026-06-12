package com.spring.placement_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentPlacementDTO {

    private Long studentId;

    private String name;

    private String department;

    private Double cgpa;

    private String companyName;
}