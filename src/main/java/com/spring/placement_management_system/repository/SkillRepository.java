package com.spring.placement_management_system.repository;



import com.spring.placement_management_system.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository
        extends JpaRepository<Skill, Long> {

    List<Skill> findByStudentStudentId(Long studentId);

    boolean existsByStudentStudentId(Long studentId);
    boolean existsBySkillIdAndStudentStudentId(Long skillId, Long studentId);
}