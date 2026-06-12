package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.CurrentUser;
import com.spring.placement_management_system.dto.JobDTO;
import com.spring.placement_management_system.dto.response.JobStatsDTO;
import com.spring.placement_management_system.entity.Company;
import com.spring.placement_management_system.entity.Job;
import com.spring.placement_management_system.dto.mapper.JobMapper;
import com.spring.placement_management_system.entity.Student;
import com.spring.placement_management_system.exception.CompanyException;
import com.spring.placement_management_system.exception.JobException;
import com.spring.placement_management_system.repository.ApplicationRepository;
import com.spring.placement_management_system.repository.CompanyRepository;
import com.spring.placement_management_system.repository.JobRepository;
import com.spring.placement_management_system.repository.StudentRepository;
import com.spring.placement_management_system.service.ApplicationService;
import com.spring.placement_management_system.service.EligibilityService;
import com.spring.placement_management_system.service.JobService;
import com.spring.placement_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.spring.placement_management_system.dto.response.JobStatsDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobMapper jobMapper;
    private final UserService  userService;
    private final EligibilityService eligibilityService;
    private final StudentRepository studentRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public JobDTO createJob(JobDTO jobDto) {

        Company company = companyRepository.findById(jobDto.getCompanyId())
                .orElseThrow(() ->
                        new CompanyException(
                                "Company not found with id : "
                                        + jobDto.getCompanyId()));

        Job job = jobMapper.toEntity(jobDto);

        job.setCompany(company);
        job.setCreatedAt(LocalDateTime.now());

        Job savedJob = jobRepository.save(job);

        return jobMapper.toDto(savedJob);
    }

    @Override
    public JobDTO getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobException(
                                "Job not found with id : "
                                        + jobId));

        return jobMapper.toDto(job);
    }

    @Override
    public List<JobDTO> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(jobMapper::toDto)
                .toList();
    }

    @Override
    public List<JobDTO> getJobsByCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
        List<Job> jobs = jobRepository.findByCompanyCompanyId(companyId);
            if (jobs.isEmpty()) {
                throw new JobException("No jobs found with company id : " + companyId);
            }
            return jobs.stream()
                    .map(jobMapper::toDto)
                    .toList();
        }
        else{
            throw new CompanyException("Company not found with id : " + companyId);
        }
    }

    @Override
    public JobDTO updateJob(
            Long jobId,
            JobDTO jobDto) {
//        System.out.println("call in service-------------------------------------------------------------------------------------------------");
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobException(
                                "Job not found with id : "
                                        + jobId));


        jobMapper.updateEntity(job, jobDto);

        Job updatedJob = jobRepository.save(job);

        return jobMapper.toDto(updatedJob);
    }

    @Override
    public void deleteJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobException(
                                "Job not found with id : "
                                        + jobId));

        jobRepository.delete(job);
    }

    @Override
    public List<JobDTO> getAllAvailableJobs() {
        return jobRepository.findByApplicationDeadlineGreaterThanEqual(LocalDate.now())
                .stream()
                .map(jobMapper::toDto)
                .toList();
    }

    @Override
    public List<JobDTO> getEligibleJobs() {
        CurrentUser currentUser =  userService.getCurrentUser();
        Student student = studentRepository.findByUserUserId(currentUser.getUserId()).orElseThrow(() -> new RuntimeException("Student not found"));
        List<Job> jobs = jobRepository.findByApplicationDeadlineGreaterThanEqual(LocalDate.now());
        List<Job> ans = new ArrayList<>();
        for (Job job : jobs) {
            if(eligibilityService.isEligible(student.getStudentId(),job.getJobId())){
                ans.add(job);
            }
        }
        return ans.stream().map(jobMapper::toDto).toList();
    }

    @Override
    public JobStatsDTO getJobStats(Long jobId) {
        Long applicationCount = applicationRepository.countByJobJobId(jobId);
        Long ShortListedCount = applicationRepository.countByJobJobIdAndStatus(jobId, StatusConstants.SHORTLISTED_FOR_TEST);
        Long AssessmentPendingCount = applicationRepository.countByJobJobIdAndStatus(jobId, StatusConstants.SHORTLISTED_FOR_TEST);
        AssessmentPendingCount += applicationRepository.countByJobJobIdAndStatus(jobId, StatusConstants.SHORTLISTED_FOR_INTERVIEW);
        Long SelectedCount = applicationRepository.countByJobJobIdAndStatus(jobId, StatusConstants.SELECTED);
        Long RejectedCount = applicationRepository.countByJobJobIdAndStatus(jobId, StatusConstants.REJECTED);
        return JobStatsDTO.builder()
                .applications(applicationCount)
                .shortlisted(ShortListedCount)
                .assessmentsPending(AssessmentPendingCount)
                .selected(SelectedCount)
                .rejected(RejectedCount)
                .build();

    }


}