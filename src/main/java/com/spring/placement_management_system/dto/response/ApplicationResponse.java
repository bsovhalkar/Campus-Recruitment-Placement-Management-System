package com.spring.placement_management_system.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApplicationResponse {

    private Long applicationId;

    private Long studentId;

    private String studentName;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String status;

    private LocalDateTime appliedAt;

    private String remarks;

}