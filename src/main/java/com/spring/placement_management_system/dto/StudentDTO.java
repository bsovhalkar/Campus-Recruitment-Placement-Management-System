package com.spring.placement_management_system.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {

    private Long studentId;

    private UserDTO user;

    private List<SkillDTO> skills;

    private ResumeDTO resume;

    private String phone;
    private String department;
    private Double cgpa;
    private Integer graduationYear;
    private String gender;
    private String address;
    private String placementStatus;
    private Integer activeBacklogs;
}