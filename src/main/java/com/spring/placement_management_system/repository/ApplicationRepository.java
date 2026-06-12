package com.spring.placement_management_system.repository;


import com.spring.placement_management_system.entity.Application;
import com.spring.placement_management_system.entity.Job;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.constant.StatusConstants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//import static com.spring.placement_management_system.constant.StatusConstants.SHORTLISTED;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByStudentAndJob(
            Student student,
            Job job
    );


    Optional<List<Application>> findByJobJobIdAndStatus(
            Long jobId,
            StatusConstants status
    );


    Optional<Application> findByStudentAndJob(
            Student student,
            Job job
    );

    Optional<List<Application>> findByStudentStudentId(
            Long studentId
    );

    Optional<List<Application>> findByJobJobId(
            Long jobId
    );

    Long countByStatus(StatusConstants status);

    Optional<List<Application>> findByStatus(
            StatusConstants status
    );

    Long countByJobJobId(
            Long jobId
    );

    Long countByJobJobIdAndStatus(Long jobId,StatusConstants status);

    Optional<Application>
    findByJobJobIdAndStudentStudentId(
            Long jobId,
            Long studentId);

    Optional<List<Application>> findByStudentStudentIdAndStatus(Long studentId, StatusConstants status);


}