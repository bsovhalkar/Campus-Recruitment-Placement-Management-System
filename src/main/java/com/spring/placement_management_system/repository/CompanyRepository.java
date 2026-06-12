package com.spring.placement_management_system.repository;

import com.spring.placement_management_system.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository
        extends JpaRepository<Company, Long> {
}