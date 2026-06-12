package com.spring.placement_management_system.dto;

import com.spring.placement_management_system.dto.JobDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    private Long companyId;

    private String companyName;

    private String email;

    private String website;

    private String location;

    private List<JobDTO> jobs;

    private String description;
}