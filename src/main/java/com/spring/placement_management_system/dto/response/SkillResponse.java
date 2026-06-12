package com.spring.placement_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SkillResponse {

    private Long skillId;

    private Long studentId;

    private String skillName;

    private String proficiencyLevel;

}