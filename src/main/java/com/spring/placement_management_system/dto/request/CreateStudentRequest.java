package com.spring.placement_management_system.dto.request;

import lombok.Data;

@Data
public class CreateStudentRequest {

    private Long userId;

    private String phone;

    private String department;

    private Double cgpa;

    private Integer graduationYear;

    private String gender;

    private String address;

    private Integer activeBacklogs;

}