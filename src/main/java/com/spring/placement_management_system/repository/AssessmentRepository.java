package com.spring.placement_management_system.repository;

import com.spring.placement_management_system.constant.AssessmentType;
import com.spring.placement_management_system.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AssessmentRepository
        extends JpaRepository<Assessment, Long> {

    List<Assessment> findByJobId(Long jobId);

    boolean existsByJobIdAndAssessmentDateAndAssessmentTime(
            Long jobId,
            LocalDate assessmentDate,
            LocalTime assessmentTime
    );
    boolean existsByJobIdAndAssessmentDateAndAssessmentTimeAndAssessmentType(
            Long jobId,
            LocalDate assessmentDate,
            LocalTime assessmentTime,
            AssessmentType assessmentType
    );

    Optional<Assessment> findByAssessmentId(Long assessmentId);







}