package com.spring.placement_management_system.dto.mapper;

import com.spring.placement_management_system.dto.JobDTO;
import com.spring.placement_management_system.entity.Company;
import com.spring.placement_management_system.entity.Job;
import com.spring.placement_management_system.exception.CompanyException;
import com.spring.placement_management_system.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobMapper {

    private final CompanyRepository companyRepository;

    public Job toEntity(JobDTO dto) {

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company not found with id : "
                                        + dto.getCompanyId()));

        return Job.builder()
                .jobId(dto.getJobId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .packageOffered(dto.getPackageOffered())
                .minimumCgpa(dto.getMinimumCgpa())
                .vacancies(dto.getVacancies())
                .applicationDeadline(dto.getApplicationDeadline())
                .location(dto.getLocation())
                .passingYear(dto.getPassingYear())
                .backlogAllowed(dto.getBacklogAllowed())
                .backlogAllowedCount(dto.getBacklogAllowedCount())
                .eligibleDepartments(dto.getEligibleDepartments())
                .company(company)
                .build();
    }

    public JobDTO toDto(Job job) {
//        System.out.println("JobMapper toDto-----------------------------------------------------------------------------------------");
        return JobDTO.builder()
                .jobId(job.getJobId())
                .title(job.getTitle())
                .description(job.getDescription())
                .packageOffered(job.getPackageOffered())
                .minimumCgpa(job.getMinimumCgpa())
                .vacancies(job.getVacancies())
                .applicationDeadline(job.getApplicationDeadline())
                .location(job.getLocation())
                .passingYear(job.getPassingYear())
                .backlogAllowed(job.getBacklogAllowed())
                .backlogAllowedCount(job.getBacklogAllowedCount())
                .eligibleDepartments(job.getEligibleDepartments())
                .companyId(job.getCompany().getCompanyId())
                .companyName(job.getCompany().getCompanyName())
                .build();
    }

    public void updateEntity(
            Job job,
            JobDTO dto) {


        if(dto.getTitle() != null) {
            job.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null) {
            job.setDescription(dto.getDescription());
        }
        if(dto.getPackageOffered() != null) {
            job.setPackageOffered(dto.getPackageOffered());
        }
        if(dto.getMinimumCgpa() != null) {
            job.setMinimumCgpa(dto.getMinimumCgpa());
        }
        if(dto.getVacancies() != null) {
            job.setVacancies(dto.getVacancies());
        }
        if(dto.getApplicationDeadline() != null) {
            job.setApplicationDeadline(dto.getApplicationDeadline());
        }
        if(dto.getLocation() != null) {
            job.setLocation(dto.getLocation());
        }
        if(dto.getPassingYear() != null) {
            job.setPassingYear(dto.getPassingYear());
        }
        if(dto.getBacklogAllowed() != null) {
            job.setBacklogAllowed(dto.getBacklogAllowed());
        }
        if(dto.getBacklogAllowedCount() != null) {
            job.setBacklogAllowedCount(dto.getBacklogAllowedCount());
        }
        if(dto.getEligibleDepartments() != null) {
            job.setEligibleDepartments(dto.getEligibleDepartments());
        }
        if(dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() ->
                            new CompanyException(
                                    "Company not found with id : "
                                            + dto.getCompanyId()));
            job.setCompany(company);
        }

    }
}