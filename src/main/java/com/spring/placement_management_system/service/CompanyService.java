package com.spring.placement_management_system.service;

import com.spring.placement_management_system.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {

    CompanyDTO createCompany(CompanyDTO companyDto);

    CompanyDTO getCompanyById(Long companyId);

    List<CompanyDTO> getAllCompanies();

    CompanyDTO updateCompany(
            Long companyId,
            CompanyDTO companyDto);

    void deleteCompany(Long companyId);
}