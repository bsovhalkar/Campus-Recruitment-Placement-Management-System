package com.spring.placement_management_system.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyJobRequest {

    private Long studentId;

    private Long jobId;

}