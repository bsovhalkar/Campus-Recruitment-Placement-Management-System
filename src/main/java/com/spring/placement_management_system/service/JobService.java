package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.JobDTO;
import com.spring.placement_management_system.dto.response.JobStatsDTO;

import java.util.List;

public interface JobService {

    JobDTO createJob(JobDTO jobDto);

    JobDTO getJobById(Long jobId);

    List<JobDTO> getAllJobs();

    List<JobDTO> getJobsByCompany(Long companyId);

    JobDTO updateJob(Long jobId, JobDTO jobDto);

    void deleteJob(Long jobId);

    List<JobDTO> getAllAvailableJobs();

    List<JobDTO> getEligibleJobs();

    JobStatsDTO getJobStats(Long jobId);
}