package com.spring.placement_management_system.service;


public interface EligibilityService {

    boolean isEligible(
            Long studentId,
            Long jobId);
}