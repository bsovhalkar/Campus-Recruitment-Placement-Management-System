package com.spring.placement_management_system.dto.mapper;

import com.spring.placement_management_system.dto.SkillDTO;
import com.spring.placement_management_system.entity.Skill;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SkillMapper {

    public SkillDTO toDTO(Skill skill) {

        if (skill == null) {
            return null;
        }

        SkillDTO dto = new SkillDTO();

        dto.setSkillId(skill.getSkillId());
        dto.setSkillName(skill.getSkillName());
        dto.setProficiencyLevel(skill.getProficiencyLevel());

        return dto;
    }
    public List<SkillDTO> toDTOList(List<Skill> skills) {
        if (skills == null) {
            return null;
        }
        return skills.stream()
                .map(this::toDTO)
                .toList();
    }
}