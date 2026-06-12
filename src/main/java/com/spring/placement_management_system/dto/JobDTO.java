package com.spring.placement_management_system.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDTO {

    private Long jobId;

    private String title;

    private String description;

    private Double packageOffered;

    private Double minimumCgpa;

    private Integer vacancies;

    private LocalDate applicationDeadline;

    private String location;

    private Integer passingYear;

    private Boolean backlogAllowed;

    private Integer backlogAllowedCount;

    private String eligibleDepartments;

    private Long companyId;

    private String companyName;

}