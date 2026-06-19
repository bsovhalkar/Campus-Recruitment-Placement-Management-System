package com.spring.placement_management_system.service;


public interface EligibilityService {

    boolean isEligible(Long jobId);
    boolean isEligible2(Long studentId,Long jobId);
}