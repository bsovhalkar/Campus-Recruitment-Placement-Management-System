package com.spring.placement_management_system.service;


import com.spring.placement_management_system.constant.StatusConstants;
import com.spring.placement_management_system.dto.JobDTO;
import com.spring.placement_management_system.dto.request.ApplyJobRequest;
import com.spring.placement_management_system.dto.response.ApplicationResponse;
import com.spring.placement_management_system.exception.ApplicationException;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse applyForJob(
            Long jobId
    ) throws ApplicationException;

    ApplicationResponse withdrawApplication(
            Long applicationId
    );

    List<ApplicationResponse> getStudentApplications(StatusConstants status);

    List<ApplicationResponse> getJobApplications(
            Long jobId
    );



    List<JobDTO> getAllAvailableJobs();

    ApplicationResponse getApplicationById(Long applicationId);

    StatusConstants getApplicationStatus(Long jobId);

    List<ApplicationResponse> getAllApplications();

    StatusConstants updateApplicationStatus(Long applicationId, StatusConstants status);

}