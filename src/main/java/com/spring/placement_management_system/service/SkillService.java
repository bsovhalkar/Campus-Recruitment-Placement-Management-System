package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.SkillDTO;
import com.spring.placement_management_system.dto.request.SkillRequest;
import com.spring.placement_management_system.entity.Skill;

import java.util.List;

public interface SkillService {

    List<SkillDTO> addSkill(SkillRequest request);

    List<SkillDTO> getSkills();

    String updateSkill(Long skillId,
                      SkillRequest request);

    String deleteSkill(Long skillId);

    String getStatus();

    SkillDTO getSkillById(Long skillId);
}