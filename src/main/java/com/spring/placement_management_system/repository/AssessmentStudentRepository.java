package com.spring.placement_management_system.repository;

import com.spring.placement_management_system.constant.AssessmentStatus;
import com.spring.placement_management_system.entity.AssessmentStudent;
import com.spring.placement_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssessmentStudentRepository
        extends JpaRepository<AssessmentStudent, Long> {

    List<AssessmentStudent>
    findByAssessmentAssessmentId(
            Long assessmentId);

    List<AssessmentStudent>
    findByStudentStudentId(
            Long studentId);


    Optional<AssessmentStudent>
    findByAssessmentAssessmentIdAndStudentStudentId(
            Long assessmentId,
            Long studentId);

    List<AssessmentStudent> findByStudentStudentIdAndAssessment_StatusAndAssessment_AssessmentDateGreaterThanEqual(
            Long studentId,
            AssessmentStatus status,
            LocalDate date
    );
    @Query("""
    SELECT ast.student
    FROM AssessmentStudent ast
    WHERE ast.assessment.assessmentId = :assessmentId
   """)
    List<Student> findStudentsByAssessmentId(@Param("assessmentId") Long assessmentId);

}