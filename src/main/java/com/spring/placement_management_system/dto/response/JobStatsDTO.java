package com.spring.placement_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobStatsDTO {
    Long applications;
    Long shortlisted;
    Long assessmentsPending;
    Long selected;
    Long rejected;
}
