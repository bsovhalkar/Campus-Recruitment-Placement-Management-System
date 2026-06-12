package com.spring.placement_management_system.dto.mapper;


import com.spring.placement_management_system.dto.CompanyDTO;
import com.spring.placement_management_system.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class CompanyMapper {
    private final JobMapper jobMapper;
    public Company toEntity(CompanyDTO dto) {

        return Company.builder()
                .companyId(dto.getCompanyId())
                .companyName(dto.getCompanyName())
                .email(dto.getEmail())
                .website(dto.getWebsite())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .jobs(dto.getJobs() == null
                        ? Collections.emptyList()
                        : dto.getJobs().stream()
                        .map(jobMapper::toEntity)
                        .toList())
                .build();
    }

    public CompanyDTO toDto(Company company) {

        return CompanyDTO.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .email(company.getEmail())
                .website(company.getWebsite())
                .location(company.getLocation())
                .description(company.getDescription())
                .jobs(company.getJobs() == null
                        ? Collections.emptyList()
                        : company.getJobs().stream()
                        .map(jobMapper::toDto)
                        .toList())
                .build();
    }

    public void updateEntity(
            Company company,
            CompanyDTO dto) {

        if(dto.getCompanyName() != null){
            company.setCompanyName(dto.getCompanyName());
        }
        if(dto.getEmail() != null){
            company.setEmail(dto.getEmail());
        }
        if(dto.getWebsite() != null){
            company.setWebsite(dto.getWebsite());
        }
        if(dto.getLocation() != null){
            company.setLocation(dto.getLocation());
        }
        if(dto.getDescription() != null) {
            company.setDescription(dto.getDescription());
        }
    }
}