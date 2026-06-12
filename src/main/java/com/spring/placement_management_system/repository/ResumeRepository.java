package com.spring.placement_management_system.repository;



import com.spring.placement_management_system.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository
        extends JpaRepository<Resume, Long> {

    Optional<Resume> findByStudentStudentId(Long studentId);

    boolean existsByResumeIdAndStudentUserUserId(
            Long resumeId,
            Long userId
    );
    boolean existsByStudentStudentId(Long studentId);
}