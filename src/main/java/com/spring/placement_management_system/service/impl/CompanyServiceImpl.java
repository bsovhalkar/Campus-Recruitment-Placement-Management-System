package com.spring.placement_management_system.service.impl;

import com.spring.placement_management_system.dto.CompanyDTO;
import com.spring.placement_management_system.entity.Company;
import com.spring.placement_management_system.dto.mapper.CompanyMapper;
import com.spring.placement_management_system.exception.CompanyException;
import com.spring.placement_management_system.repository.CompanyRepository;
import com.spring.placement_management_system.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl
        implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDTO createCompany(
            CompanyDTO companyDto) {

        Company company =
                companyMapper.toEntity(companyDto);

        Company savedCompany =
                companyRepository.save(company);

        return companyMapper.toDto(savedCompany);
    }

    @Override
    public CompanyDTO getCompanyById(
            Long companyId) {

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found with id : "
                                                + companyId));

        return companyMapper.toDto(company);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {

        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toDto)
                .toList();
    }

    @Override
    public CompanyDTO updateCompany(
            Long companyId,
            CompanyDTO companyDto) {

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found with id : "
                                                + companyId));

        companyMapper.updateEntity(
                company,
                companyDto);

        Company updatedCompany =
                companyRepository.save(company);

        return companyMapper.toDto(updatedCompany);
    }

    @Override
    public void deleteCompany(
            Long companyId) {

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new CompanyException(
                                        "Company not found with id : "
                                                + companyId) {
                                });

        companyRepository.delete(company);
    }
}