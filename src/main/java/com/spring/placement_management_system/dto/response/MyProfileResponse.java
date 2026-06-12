package com.spring.placement_management_system.dto.response;

import com.spring.placement_management_system.constant.RoleConstants;
import com.spring.placement_management_system.dto.ResumeDTO;
import com.spring.placement_management_system.dto.SkillDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProfileResponse {

    // User fields
    private Long userId;
    private String name;
    private String email;
    private RoleConstants role;
    private String status;
    private LocalDateTime createdAt;

    // Student fields
    private Long studentId;
    private String phone;
    private String department;
    private Double cgpa;
    private Integer graduationYear;
    private String gender;
    private String address;
    private String placementStatus;
    private Integer activeBacklogs;

    // Nested data
    private ResumeDTO resume;
    private List<SkillDTO> skills;
    private List<ApplicationResponse> applications;
}