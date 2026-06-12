package com.spring.placement_management_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShortlistingDTO {

    private Long applicationId;

    private Long studentId;

    private String studentName;

    private Long jobId;

    private String jobTitle;

    private Double cgpa;

    private Integer skillsCount;

    private Double score;

    private Integer rank;

}