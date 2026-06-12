package com.spring.placement_management_system.repository;

import com.spring.placement_management_system.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompanyCompanyId(Long companyId);

//    List<Job> findByApplicationDeadlineAfter(LocalDate now);
    List<Job> findByApplicationDeadlineGreaterThanEqual(LocalDate date);

    Boolean existsByJobId(Long jobId);

}